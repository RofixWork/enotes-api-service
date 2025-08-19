package com.rofix.enotes_service.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import com.rofix.enotes_service.entity.FileDetails;
import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.exception.base.CustomValidationException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.repository.FileDetailsRepository;
import com.rofix.enotes_service.repository.NoteRepository;
import com.rofix.enotes_service.service.NoteServiceImpl;
import com.rofix.enotes_service.utils.LoggerUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NoteHelper {
    private final FileDetailsRepository fileDetailsRepository;
    private final NoteRepository noteRepository;
    private final Validator validatorHandler;
    @Value("${file.upload.path}")
    private String uploadFilePath;
    

    public FileDetails saveFileDetails(MultipartFile file) {

        checkFileExtension(file);

        FileDetails fileDetails = new FileDetails();
        String uploadFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        fileDetails.setOriginalFileName(file.getOriginalFilename());
        fileDetails.setDisplayFileName(getDisplayFileName(file.getOriginalFilename()));
        fileDetails.setUploadFileName(uploadFileName);
        fileDetails.setFileSize(file.getSize());

        File saveFile = new File(uploadFilePath);

        if(!saveFile.exists())
        {
            boolean isCreated = saveFile.mkdir();
            LoggerUtils.createLog(isCreated ? Level.INFO : Level.ERROR, NoteServiceImpl.class.getName(), "uploadFile", isCreated ? "Directory created successfully" : "Failed to create directory");
        }
        String uploadPath = uploadFilePath + File.separator + uploadFileName;
        fileDetails.setPath(uploadPath);
        try {
            Files.copy(file.getInputStream(), Paths.get(uploadPath));
            LoggerUtils.createLog(Level.INFO, NoteServiceImpl.class.getName(), "uploadFile", "File to upload successfully");
        } catch (IOException ex)
        {
            LoggerUtils.createLog(Level.ERROR,  NoteServiceImpl.class.getName(), "uploadFile", "Failed to upload file {}", ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }

        return fileDetailsRepository.save(fileDetails);
    }

    public FileDetails getFileDetailsOrThrow(Long id)
    {
        return fileDetailsRepository.findById(id).orElseThrow(() -> {
            LoggerUtils.createLog(Level.ERROR, NoteServiceImpl.class.getName(), "getFileDetailsOrThrow", "File not found with id: {}", id);
            return new NotFoundException("File not found with id " + id);
        });
    }

    public Note getNoteByIdAndCreatedByOrThrow(Long noteId, Long userId)
    {
        return noteRepository.findByIdAndCreatedBy(noteId, userId).orElseThrow(() -> {
            LoggerUtils.createLog(Level.ERROR, NoteServiceImpl.class.getName(), "getNoteOrThrow", "Note not found with id {} or access denied", noteId);
            return new NotFoundException("Note not found with id or access denied");
        });
    }

    public Note getNoteNotDeletedOrThrow(Long id)
    {
        return noteRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> {
            LoggerUtils.createLog(Level.ERROR, NoteServiceImpl.class.getName(), "getNoteNotDeletedOrThrow", "Note not found with id: {}", id);
            return new NotFoundException("Note not found with id " + id);
        });
    }


    private void checkFileExtension(MultipartFile file) {
        List<String> extensionAllow = List.of("pdf", "xlsx", "jpg", "png", "docx");

        if(!extensionAllow.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            LoggerUtils.createLog(Level.WARN, NoteHelper.class.getName(), "saveFileDetails", "Invalid file format!!! Upload only {}", extensionAllow);
            throw new BadRequestException("Invalid file format!!! Upload only " + extensionAllow);
        }
    }

    private String getDisplayFileName(String originalFilename) {
        String fileName = FilenameUtils.getBaseName(originalFilename),
                extension = FilenameUtils.getExtension(originalFilename);

        if(fileName.length() > 8)
        {
            fileName = fileName.substring(0, 7);
        }

        return fileName + "." + extension;
    }

    public String getContentType(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
//        "pdf", "xlsx", "jpg", "png", "docx"
        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "jpg" -> "image/jpeg";
            case "png" -> "image/png";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default -> "application/octet-stream";
        };
    }

    public NoteRequestDTO getNoteRequestDTO(String noteRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NoteRequestDTO noteRequestDTO = objectMapper.readValue(noteRequest, NoteRequestDTO.class);

        Set<ConstraintViolation<NoteRequestDTO>> violations = validatorHandler.validate(noteRequestDTO);

        if(!violations.isEmpty())
        {
            throw new CustomValidationException(violations);
        }
        return noteRequestDTO;
    }
}
