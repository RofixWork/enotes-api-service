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
                         <a class="button" href="#">Verify Account</a>
                     </div>
                     <div class="footer">
                         <h3>Enotes.com</h3>
                     </div>
                 </div>
             </body>
            
             </html>
            """;
}
