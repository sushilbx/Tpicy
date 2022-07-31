package com.tpicy.models;

public class SessionModel {


        public String id;
        private String name;
        private String mobile;
        private String email;
        private String gender;
        private String role;
        private String status;
        private String created_at;
        private String updated_at;
        private String api_secret;
        private String api_key;

        public SessionModel(String id, String name, String mobile, String email, String gender, String role, String status, String created_at, String updated_at, String api_secret, String api_key) {
            this.id = id;
            this.name = name;
            this.mobile = mobile;
            this.email = email;
            this.gender = gender;
            this.role = role;
            this.status = status;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.api_secret = api_secret;
            this.api_key = api_key;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getApi_secret() {
            return api_secret;
        }

        public void setApi_secret(String api_secret) {
            this.api_secret = api_secret;
        }

        public String getApi_key() {
            return api_key;
        }

        public void setApi_key(String api_key) {
            this.api_key = api_key;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }





