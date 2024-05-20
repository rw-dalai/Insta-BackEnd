package com.example.insta.foundation;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public class MediaUtil {
  public static MultipartFile[] getNonNullMultiPart(@Nullable MultipartFile[] medias) {
    if (medias == null) {
      return new MultipartFile[0];
    }
    return medias;
  }
}
