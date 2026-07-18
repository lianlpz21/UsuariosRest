package com.usuarios.UsuariosRest.services;

import com.usuarios.UsuariosRest.models.UsuarioModel;
import com.usuarios.UsuariosRest.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository userRepository;

    public ArrayList<UsuarioModel> getUsuario() {
        return (ArrayList<UsuarioModel>) userRepository.findAll();
    }

    public UsuarioModel saveUser(UsuarioModel user) {
        return userRepository.save(user);
    }

    public Optional<UsuarioModel> getById(Long id) {
        return userRepository.findById(id);
    }

    public UsuarioModel updateById(UsuarioModel request, Long id) {
        Optional<UsuarioModel> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UsuarioModel user = optionalUser.get();

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());

            return userRepository.save(user);
        }

        throw new UserNotFoundException(
                "El usuario que intenta actualizar no existe con el id " + id
        );
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(
                    "El usuario que intenta eliminar no existe con el id " + id
            );
        }

        userRepository.deleteById(id);
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}