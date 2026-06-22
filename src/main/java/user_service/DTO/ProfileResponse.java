package user_service.DTO;

import java.util.UUID;

public record ProfileResponse(String name, String email, Long userId) {
}
