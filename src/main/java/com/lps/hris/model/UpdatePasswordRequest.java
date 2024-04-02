package com.lps.hris.model;

public record UpdatePasswordRequest(String password, String email, String otp) {

}
