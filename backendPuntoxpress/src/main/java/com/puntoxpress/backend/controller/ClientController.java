package com.puntoxpress.backend.controller;

import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.model.dto.ResponseDTO;
import com.puntoxpress.backend.service.ClientService;
import com.puntoxpress.backend.service.LocationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@Validated
@Slf4j
public class ClientController {
    private final ClientService clientService;
    
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDTO<?>> crear(@Valid @RequestBody Client dataRequest) {
        log.info("Create initiated :{}", dataRequest.toString());
        ResponseEntity<ResponseDTO<?>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        ResponseDTO<Client> response = new ResponseDTO<>();
        HttpStatus httpStatus;
        try {
            Client save = clientService.crear(dataRequest);
            httpStatus = HttpStatus.CREATED;
            response.setCode(httpStatus.value());
            response.setMessage("Registro creado exitosamente");
            response.setData(save);
        } catch (Exception e) {
            log.error("Error creating balance: {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setCode(httpStatus.value());
            response.setMessage("Ocurrio un error al crear");
        }
        responseEntity = ResponseEntity.status(httpStatus).body(response);
        log.info("Response: {}", responseEntity);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDTO<?>> actualizar(@PathVariable UUID id, @Valid @RequestBody Client dataRequest) {
        log.info("Update initiated :{}", dataRequest.toString());
        ResponseEntity<ResponseDTO<?>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        ResponseDTO<Client> response = new ResponseDTO<>();
        HttpStatus httpStatus;
        try {
            Client update = clientService.actualizar(id, dataRequest);
            if (update == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                response.setCode(httpStatus.value());
                response.setMessage("Registro no encontrado");
            } else {
                httpStatus = HttpStatus.OK;
                response.setCode(httpStatus.value());
                response.setMessage("Registro actualizado exitosamente");
                response.setData(update);
            }
        } catch (Exception e) {
            log.error("Error updating registro: {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setCode(httpStatus.value());
            response.setMessage("Ocurrió un error al actualizar");
        }
        responseEntity = ResponseEntity.status(httpStatus).body(response);
        log.info("Response: {}", responseEntity);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDTO<?>> eliminar(@PathVariable UUID id) {
        log.info("Delete initiated with id: {}", id);
        ResponseEntity<ResponseDTO<?>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        ResponseDTO<Client> response = new ResponseDTO<>();
        HttpStatus httpStatus;
        try {
            boolean isDeleted = clientService.eliminar(id);
            if (isDeleted) {
                httpStatus = HttpStatus.OK;
                response.setCode(httpStatus.value());
                response.setMessage("Registro eliminado exitosamente");
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                response.setCode(httpStatus.value());
                response.setMessage("Registro no encontrado");
            }
        } catch (Exception e) {
            log.error("Error deleting registro: {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setCode(httpStatus.value());
            response.setMessage("Ocurrió un error al eliminar");
        }
        responseEntity = ResponseEntity.status(httpStatus).body(response);
        log.info("Response: {}", responseEntity);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/listar")
    public ResponseEntity<ResponseDTO<?>> listar(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "100") int size) {
        log.info("List registros initiated with page: {} and size: {}", page, size);
        ResponseEntity<ResponseDTO<?>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        ResponseDTO<List<Client>> response = new ResponseDTO<>();
        HttpStatus httpStatus;
        try {
            Pageable pageableRequest = PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Order.desc("createdAt"))
            );
            Page<Client> registrosPage = clientService.obtenerLista(pageableRequest);
            response.setCode(HttpStatus.OK.value());
            response.setMessage("Listado de registros");
            response.setData(registrosPage.getContent());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error listing registros: {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setCode(httpStatus.value());
            response.setMessage("Ocurrió un error al listar");
        }
        responseEntity = ResponseEntity.status(httpStatus).body(response);
        log.info("Response: {}", responseEntity);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseDTO<?>> buscarPorId(@PathVariable UUID id) {
        log.info("Buscar Registro por ID iniciado con id: {}", id);
        ResponseEntity<ResponseDTO<?>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        ResponseDTO<Client> response = new ResponseDTO<>();
        HttpStatus httpStatus;
        try {
            Client registro = clientService.buscarPorId(id);
            if (registro == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                response.setCode(httpStatus.value());
                response.setMessage("Registro no encontrado");
            } else {
                httpStatus = HttpStatus.OK;
                response.setCode(httpStatus.value());
                response.setMessage("Registro encontrado");
                response.setData(registro);
            }
        } catch (Exception e) {
            log.error("Error buscando registro por ID: {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setCode(httpStatus.value());
            response.setMessage("Ocurrió un error al buscar el registro");
        }
        responseEntity = ResponseEntity.status(httpStatus).body(response);
        log.info("Response: {}", responseEntity);
        return responseEntity;
    }
}
