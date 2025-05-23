package com.kartingrm.reservas_comprobantes_service.service;

import com.kartingrm.reservas_comprobantes_service.entity.Reserva;
import com.kartingrm.reservas_comprobantes_service.model.ClienteDTO;
import com.kartingrm.reservas_comprobantes_service.model.PlanDTO;
import com.kartingrm.reservas_comprobantes_service.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    RestTemplate restTemplate;


    public List<Reserva> getReservas() {
        return reservaRepository.findAll();
    }

    public Reserva getReservaById(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isEmpty()) throw new EntityNotFoundException("Reserva " + id + " no encontrada");

        return reserva.get();
    }

    // Obtener todas las reservas de un cliente segun id
    public List<Reserva> getReservasByIdReservante(Long idCliente) {
        return reservaRepository.findReservasByIdReservante(idCliente);
    }


    // Crear reserva
    // Considera que no existan reservas previas en el horario, ademas ingresa automaticamente la hora de fin segun el plan
    // Verifica que sea feriado????


    // El cuerpo ya tiene id cliente reservante e id plan
    public Reserva createReserva(Reserva reserva) {
        // Obtener Cliente
        ClienteDTO cliente = restTemplate.getForObject("http://cliente-desc-frecu-service/api/cliente-service/cliente/" + reserva.getIdReservante(), ClienteDTO.class);
        // Obtener Plan
        PlanDTO plan = restTemplate.getForObject("http://plan-service/api/plan/planes/" + reserva.getIdPlan(), PlanDTO.class);

        // Verificar parametros validos
        if (reserva.getHoraInicio() != null && cliente != null && plan != null) {

            // Determinar hora de fin sumando minutos del plan a la hora inicial
            LocalTime horaInicio = reserva.getHoraInicio();
            LocalTime horaFinalCalculada = horaInicio.plusMinutes(plan.getDuracionTotal());
            reserva.setHoraFin(horaFinalCalculada);

            // Verificar que no existan reservas enter ambas horas
            if (existeReservaEntreDosHoras(reserva.getFecha(), horaInicio, horaFinalCalculada))
                throw new IllegalStateException("Ya existe una reserva con ese horario");



            // Verificar Horario de inicio y fin validos.
            //  Lunes a Viernes: 14:00 a 22:00
            //  Sabados, Domingos, Feriados: 10:00 a 22:00
            boolean esFinDeSemana = reserva.getFecha().getDayOfWeek().getValue() >= 6;// Determina fin de semana o no

            // Obtener bool si es feriado
            boolean esFeriado = restTemplate.getForObject("http://dias-especiales-service/api/dias-especiales-service/dias-feriados/esFeriado?fecha=" + reserva.getFecha(),Boolean.class);
            if (esFeriado || esFinDeSemana){// Horario fin de semana o feriado
                // Horario antes o despues del horario de servicio
                if (horaInicio.isBefore(LocalTime.of(10,00,00)) || horaFinalCalculada.isAfter(LocalTime.of(22,00,00))) {
                    throw new IllegalStateException("Horario incorrecto. Domingos, sábados y feriados: 10:00 a 22:00");
                }
            } else { // Horario semana
                if (horaInicio.isBefore(LocalTime.of(14,00,00)) || horaFinalCalculada.isAfter(LocalTime.of(22,00,00))) {
                    throw new IllegalStateException("Horario incorrecto. Lunes a Viernes: 14:00 a 22:00");
                }
            }


            return reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Cliente no encontrado, plan no existe, o hora errónea");
        }
    }


    // Update de valor de reserva. Permite cambio de plan y de cliente reservante
    public Reserva updateReserva(Long id, Reserva reserva) {
        Optional<Reserva> reservaOriginalOptional = reservaRepository.findById(id);
        if (reservaOriginalOptional.isEmpty()) throw new EntityNotFoundException("Reserva id " + id + " no encontrado");

        // Reserva actualizada conserva id de la reserva original
        reserva.setId(id);

        // Calculo de hora final segun nueva hora o plan
        // Obtener Plan
        PlanDTO plan = restTemplate.getForObject("http://plan-service/api/plan/planes/" + reserva.getIdPlan(), PlanDTO.class);

        // Solo permite actualizar la hora inicial
        // Determinar hora de fin sumando minutos del plan a la hora inicial
        LocalTime horaInicio = reserva.getHoraInicio();
        LocalTime horaFinalCalculada = horaInicio.plusMinutes(plan.getDuracionTotal());
        reserva.setHoraFin(horaFinalCalculada);

        // Verificar que no existan reservas enter ambas horas
        if (existeReservaEntreDosHoras(reserva.getFecha(), horaInicio, horaFinalCalculada))
            throw new IllegalStateException("Ya existe una reserva con ese horario");

        // Verificar horario valido de atencion
        boolean esFinDeSemana = reserva.getFecha().getDayOfWeek().getValue() >= 6;// Determina fin de semana o no
        boolean esFeriado = restTemplate.getForObject("http://dias-especiales-service/api/dias-especiales-service/dias-feriados/esFeriado?fecha=" + reserva.getFecha(),Boolean.class);
        if (esFeriado || esFinDeSemana){// Horario fin de semana o feriado
            // Horario antes o despues del horario de servicio
            if (horaInicio.isBefore(LocalTime.of(10,00,00)) || horaFinalCalculada.isAfter(LocalTime.of(22,00,00))) {
                throw new IllegalStateException("Horario incorrecto. Domingos, sábados y feriados: 10:00 a 22:00");
            }
        } else { // Horario semana
            if (horaInicio.isBefore(LocalTime.of(14,00,00)) || horaFinalCalculada.isAfter(LocalTime.of(22,00,00))) {
                throw new IllegalStateException("Horario incorrecto. Lunes a Viernes: 14:00 a 22:00");
            }
        }

        return reservaRepository.save(reserva);
    }


    public boolean deleteReserva(Long id) {
        Optional<Reserva> reservaOriginalOptional = reservaRepository.findById(id);
        if (reservaOriginalOptional.isEmpty()) throw new EntityNotFoundException("Reserva id " + id + " no encontrado");

        reservaRepository.deleteById(id);
        return true;
    }



    //  Metodos privados. Logica interna

    // Obtener reservas existentes entre dos horas de un dia
    // Se usa en como condicion para crear reservas
    private boolean existeReservaEntreDosHoras(LocalDate fecha, LocalTime horaInicio, LocalTime horaFinal) {
        return reservaRepository.existeReservaEntreDosHoras(fecha, horaInicio, horaFinal);
    }


    // Metodos para obtener info para reportes

}
