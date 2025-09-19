package kg.attractor.instagram.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {
    private MultipartFile file;
    private String fileName;
}
