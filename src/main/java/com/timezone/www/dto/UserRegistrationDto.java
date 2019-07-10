package com.timezone.www.dto;

import com.timezone.www.contraint.FieldMatch;


    @FieldMatch.List({
            @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
            @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
    })
    public class UserRegistrationDto {

        private String userName;

        private String password;

        private String confirmPassword;

        private String email;

        private String confirmEmail;

        private Boolean terms;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getConfirmEmail() {
            return confirmEmail;
        }

        public void setConfirmEmail(String confirmEmail) {
            this.confirmEmail = confirmEmail;
        }

        public Boolean getTerms() {
            return terms;
        }

        public void setTerms(Boolean terms) {
            this.terms = terms;
        }

    }

