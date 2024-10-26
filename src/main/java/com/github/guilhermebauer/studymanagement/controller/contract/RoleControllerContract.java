package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.service.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.guilhermebauer.studymanagement.service.RoleService;

/**
 * API contract for role management operations, providing endpoints to create
 * and retrieve roles by name.
 *
 * <p>This interface defines endpoints for creating a new role and finding an
 * existing role by its name, with JSON as the request and response format.
 */

public interface RoleControllerContract {

    /**
     * Creates a new role based on the provided {@link RoleVO} data.
     *
     * <p>This endpoint accepts a JSON request to create a role and responds with
     * the created role details in JSON format.
     *
     * @param role The {@link RoleVO} object containing information about the role to be created.
     * @return {@link ResponseEntity} containing the created {@link RoleVO} and HTTP status.
     * @see RoleEntity
     * @see RoleVO
     * @see RoleService
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new Role",
            description = "Creates a new Role and returns the created role object.",
            tags = "role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = RoleVO.class))),
            @ApiResponse(responseCode = "400", description = "Role All Ready Register Exception when the role name is duplicated.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Role Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<RoleVO> createRole(@RequestBody RoleVO role);

    /**
     * Finds an existing role by its name.
     *
     * <p>This endpoint retrieves the role information based on the specified role name.
     *
     * @param name The name of the role to retrieve.
     * @return {@link ResponseEntity} containing the retrieved {@link RoleVO} and HTTP status.
     * @see RoleEntity
     * @see RoleVO
     * @see RoleService
     */

    @GetMapping(value="/findByRoleName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find role by name",
            description = "Find role by name, the role will be retrieved by name",
            tags = "role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = RoleVO.class))),
            @ApiResponse(responseCode = "404", description = "Role Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<RoleVO> findRoleByName(@PathVariable(value = "name") String name);
}
