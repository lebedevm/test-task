package de.comparus.testtask.annotation;

import de.comparus.testtask.dto.UserDto;
import de.comparus.testtask.exception.ControllerExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static de.comparus.testtask.util.SwaggerUtil.BAD_REQUEST;
import static de.comparus.testtask.util.SwaggerUtil.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Operation(summary = "Get all users data from all databases")
@Parameter(name = "username", description = "Optional user name or part of name.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = SUCCESS,
                content = {@Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))}),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ControllerExceptionHandler.ValidationMessage.class)))
})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllUsersApi {
}
