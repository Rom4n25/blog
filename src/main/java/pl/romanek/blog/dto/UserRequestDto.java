package pl.romanek.blog.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
