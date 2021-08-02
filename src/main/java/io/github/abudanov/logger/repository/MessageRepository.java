package io.github.abudanov.logger.repository;

import io.github.abudanov.logger.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m.id as id, m.createdAt as createdAt, m.queue as queue, m.body as body FROM Message m\n"
            + "WHERE (:queue IS NULL OR m.queue = :queue)\n"
            + "AND (cast(:createdFrom AS timestamp) IS NULL OR m.createdAt >= :createdFrom)\n"
            + "AND (cast(:createdUntil AS timestamp) IS NULL OR m.createdAt >= :createdUntil)\n")
    List<MessageView> getMessagesByQueueWithTime(
            @Param("queue") String queue,
            @Param("createdFrom") LocalDateTime createdFrom,
            @Param("createdUntil") LocalDateTime createdUntil
    );
}
