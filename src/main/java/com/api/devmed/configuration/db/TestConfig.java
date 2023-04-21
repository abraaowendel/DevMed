package com.api.devmed.configuration.db;

import com.api.devmed.model.entities.*;
import com.api.devmed.model.enums.Especialidade;
import com.api.devmed.model.enums.RoleName;
import com.api.devmed.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    public TestConfig(MedicoRepository medicoRepository, PacienteRepository pacienteRepository, ConsultaRepository consultaRepository, UsuarioRepository usuarioRepository,
                      RoleRepository roleRepository) {
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Endereco endereco1 = new Endereco("Rua Santa Terezinha", "98", "",
                "Centro", "Senador Rui Palmeira", "AL", "57515-000");
        Endereco endereco2 = new Endereco("Rua Agenor Frizo", "198A", "",
                "Centro", "Salto do Itararé", "PR", "84945-000");
        Endereco endereco3 = new Endereco("Rua Santo Agostinho", "104", "",
                "Centro", "Senador Rui Palmeira", "AL", "57515-000");
        Endereco endereco4 = new Endereco("Rua Manoel Friz", "18", "",
                "Centro", "São José da Tapera", "AL", "57500-000");

        Medico medico1 = new Medico(null, "Abraao Wendel", "abraao@gmail.com","82981820315", "CRM/AL 123456", Especialidade.OTORPEDIA, endereco1, true);
        Medico medico2 = new Medico(null, "Luide Mattos", "luide@gmail.com","41982242223", "CRM/PR 654322", Especialidade.GINECOLOGIA, endereco2, true);

        Paciente paciente1 = new Paciente(null, "Manoel Wendel", "manoel@gmail.com","82981545698", endereco3);
        Paciente paciente2 = new Paciente(null, "João Gordo", "joaogordo@gmail.com","82981572569", endereco4);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");

        Consulta consulta1 = new Consulta(null, paciente1, medico1, LocalDateTime.parse("20-10-2023T16:00:00", format));
        Consulta consulta2 = new Consulta(null, paciente2, medico2, LocalDateTime.parse("10-10-2023T16:00:00", format));

        Role role1 = new Role(null, RoleName.ROLE_ADMIN);
        Role role2 = new Role(null, RoleName.ROLE_USER);
        Role role3 = new Role(null, RoleName.ROLE_USER);

        Usuario usuario1 = new Usuario(null, "teste@gmail.com",
                new BCryptPasswordEncoder().encode("teste"),
                Set.of(role1, role2));

        Usuario usuario2 = new Usuario(null, "teste2@gmail.com",
                new BCryptPasswordEncoder().encode("teste"),
                Set.of(role3));

        medicoRepository.saveAll(List.of(medico1, medico2));
        pacienteRepository.saveAll(List.of(paciente1, paciente2));
        consultaRepository.saveAll(List.of(consulta1, consulta2));
        roleRepository.saveAll(List.of(role1, role2, role3));
        usuarioRepository.saveAll(List.of(usuario1, usuario2));
    }

}
