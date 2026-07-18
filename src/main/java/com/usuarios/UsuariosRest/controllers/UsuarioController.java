package com.usuarios.UsuariosRest.controllers;

import com.usuarios.UsuariosRest.models.UsuarioModel;
import com.usuarios.UsuariosRest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @GetMapping
    public ArrayList<UsuarioModel> getUsers() {
        return userService.getUsuario();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UsuarioModel> user = userService.getById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No se encontró un usuario con el id " + id);
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> saveUser(
            @RequestBody UsuarioModel user
    ) {
        UsuarioModel savedUser = userService.saveUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(
            @RequestBody UsuarioModel request,
            @PathVariable Long id
    ) {
        try {
            UsuarioModel updatedUser = userService.updateById(request, id);
            return ResponseEntity.ok(updatedUser);

        } catch (UsuarioService.UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al actualizar el usuario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);

            return ResponseEntity.ok(
                    "Usuario eliminado correctamente"
            );

        } catch (UsuarioService.UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al eliminar el usuario");
        }
    }
}