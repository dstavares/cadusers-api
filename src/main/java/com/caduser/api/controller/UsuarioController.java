package com.caduser.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caduser.api.model.Usuario;
import com.caduser.api.repository.UsuarioRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({"/usuarios"})
public class UsuarioController {
	
	private UsuarioRepository repository;
	
	UsuarioController(UsuarioRepository usuarioRepository){
		this.repository = usuarioRepository;
	}
	
	
	//Recupera todos os usuários
	@GetMapping
	public List findAll() {
		return repository.findAll();
	}
	
	
	//Recupera o usuário pelo ID
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Usuario> findById(@PathVariable long id){
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
		          .orElse(ResponseEntity.notFound().build());
	}

	
	//Criando um novo usuário
	@PostMapping
	public Usuario create(@RequestBody Usuario usuario) {
		return repository.save(usuario);
	}
	
	
	//Editando um usuário
	@PutMapping(value="/{id}")
	public ResponseEntity<Usuario> update(@PathVariable("id") long id, 
			@RequestBody Usuario usuario){
		return repository.findById(id)
		        .map(record -> {
		            record.setNome(usuario.getNome());
		            record.setCpf(usuario.getCpf());
		            record.setDatanascimento(usuario.getDatanascimento());
		            Usuario updated = repository.save(record);
		            return ResponseEntity.ok().body(updated);
		        }).orElse(ResponseEntity.notFound().build());
		  }
	
	
	//Deletando usuário
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity<?> delete(@PathVariable("id") long id){
		return repository.findById(id)
				.map(record -> {
		            repository.deleteById(id);
		            return ResponseEntity.ok().build();
		        }).orElse(ResponseEntity.notFound().build());
	}
}

