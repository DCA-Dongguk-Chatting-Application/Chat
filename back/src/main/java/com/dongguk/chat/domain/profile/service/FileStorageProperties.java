package com.dongguk.chat.domain.profile.service;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
}
