package com.example.insta.presentation.commands;

// Commands
// -----------------------------------------------------------------------------
// Commands are used to represent the input to a use case in the Service layer.
// e.g. UserRegistrationCommand, UserVerificationCommand

public abstract class Commands {

  // --- Registration ---
  //  whiten input = email.trim().toLowerCase();
  public record UserRegistrationCommand(
      String email, String password, String firstName, String lastName) {
    public String email() {
      return email.trim().toLowerCase();
    }
  }

  // --- Verification ---
  public record UserVerificationCommand(String userId, String tokenId) {}

  public record SendPostCommand(String message, MediaMetaCommand[] mediasMeta) {}

  //  public record UploadAvatarCommand(
  //      MediaMetaCommand avatarMeta
  //  ) { }

  public record MediaMetaCommand(
      String filename, String mimeType, long size, int width, int height) {}
}
