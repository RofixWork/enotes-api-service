package com.rofix.enotes_service.config;

public class AppConstants {
    public static final String PAGE_NUMBER = "1";
    public static final String PAGE_SIZE = "5";
    public static final String TEMPLATE_VERIFY_ACCOUNT = """
             <!DOCTYPE html>
             <html>
             <head>
                 <style>
                     body {
                         font-family: Arial, sans-serif;
                         line-height: 1.6;
                         color: #333;
                     }
            
                     .container {
                         max-width: 600px;
                         margin: 0 auto;
                         padding: 20px;
                         border: 1px solid #ddd;
                         border-radius: 8px;
                     }
            
                     .header {
                         background-color: #f4f4f4;
                         padding: 10px;
                         text-align: center;
                     }
            
                     .content {
                         padding: 20px;
                     }
            
                     .footer {
                         text-align: center;
                         font-size: 12px;
                         color: #888;
                         margin-top: 20px;
                     }
            
                     .button {
                         display: inline-block;
                         padding: 10px 20px;
                         background-color: #007BFF;
                         color: #fff;
                         text-decoration: none;
                         border-radius: 5px;
                     }
                 </style>
             </head>
            
             <body>
                 <div class="container">
                     <div class="header">
                         <h1>Hi, <b>%s</b></h1>
                     </div>
                     <div class="content">
                         <h2>Your account has been successfully registered.</h2>
                         <h3>Please click the link below to verify your account:</h3>
                         <a class="button" href="http://localhost:8080/api/v1/auth/verify?uid=%d&vc=%s">Verify Account</a>
                     </div>
                     <div class="footer">
                         <h3>Enotes.com</h3>
                     </div>
                 </div>
             </body>
             </html>
            """;
    public static final String TEMPLATE_RESET_PASSWORD = """
             <!DOCTYPE html>
                     <html lang="en">
                     <head>
                         <meta charset="UTF-8">
                         <meta name="viewport" content="width=device-width, initial-scale=1.0">
                         <title>Password Reset</title>
                         <style>
                             body {
                                 font-family: 'Inter', sans-serif;
                                 line-height: 1.6;
                                 color: #4b5563;
                                 background-color: #f3f4f6;
                                 margin: 0;
                                 padding: 0;
                             }
            
                             .container {
                                 max-width: 600px;
                                 margin: 40px auto;
                                 padding: 24px;
                                 background-color: #fff;
                                 border-radius: 12px;
                                 box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                             }
            
                             .header {
                                 background-color: #e5e7eb;
                                 padding: 20px;
                                 text-align: center;
                                 border-radius: 8px 8px 0 0;
                             }
            
                             .header h1 {
                                 color: #1f2937;
                                 font-size: 24px;
                                 margin: 0;
                             }
            
                             .content {
                                 padding: 24px 20px;
                                 text-align: center;
                             }
            
                             .content p {
                                 font-size: 16px;
                                 margin-bottom: 24px;
                             }
            
                             .button-container {
                                 text-align: center;
                                 margin-bottom: 24px;
                             }
            
                             .button {
                                 display: inline-block;
                                 padding: 12px 24px;
                                 background-color: #3b82f6;
                                 color: #fff;
                                 text-decoration: none;
                                 border-radius: 8px;
                                 font-size: 16px;
                                 font-weight: bold;
                                 transition: background-color 0.3s ease;
                             }
            
                             .button:hover {
                                 background-color: #2563eb;
                             }
            
                             .footer {
                                 text-align: center;
                                 font-size: 12px;
                                 color: #9ca3af;
                                 margin-top: 20px;
                                 border-top: 1px solid #e5e7eb;
                                 padding-top: 20px;
                             }
                         </style>
                     </head>
            
                     <body>
                         <div class="container">
                             <!-- Email Header -->
                             <div class="header">
                                 <h1>Password Reset Request</h1>
                             </div>
            
                             <!-- Email Body -->
                             <div class="content">
                                 <p>Hi, <b>%s</b></p>
                                 <p>We received a request to reset the password for your account. If you did not make this request, you can safely ignore this email.</p>
                                 <p>To proceed with the password reset, please click the button below:</p>
                                 <div class="button-container">
                                     <a class="button" href="%s">Reset Password</a>
                                 </div>
                                 <p>If the button doesn't work, you can copy and paste the following link into your browser:</p>
                                 <p><small>%s</small></p>
                                 <p>For your security, we recommend that you do not share this link with anyone.</p>
                             </div>
            
                             <!-- Email Footer -->
                             <div class="footer">
                                 <p>This email was sent by Enotes.com. Please do not reply to this email.</p>
                             </div>
                         </div>
                     </body>
                     </html>
    """;
}
