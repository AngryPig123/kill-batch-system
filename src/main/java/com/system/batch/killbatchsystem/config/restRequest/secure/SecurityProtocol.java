package com.system.batch.killbatchsystem.config.restRequest.secure;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.system.batch.killbatchsystem.config.restRequest.secure
 * fileName       : SecurityProtocol
 * author         : AngryPig123
 * date           : 26. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 25.        AngryPig123       최초 생성
 */
@Getter
@Builder
public class SecurityProtocol {
    private int id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return name + " => " + description;
    }
}
