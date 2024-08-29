package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Todo> findTodosWithPagination(int page, int size) {
        String sql =
                """
                        SELECT
                        t.id,
                        t.todo,
                        t.password,
                        t.created_at,
                        t.updated_at,
                        m.id as manager name,
                        m.email as manager_email,
                        m.created_at as manager_created_at,
                        m.updated_at as manager_updated_at
                        FROM todo2 t
                        JOIN manager m ON t.manager_id = m.id
                        ORDER BY t.updated_at DESC
                        LIMIT ? OFFSET?
                        """;
        int offset = page * size;
        return jdbcTemplate.query(sql, rowMapper(), size, offset);


    }

    public Todo save(Todo entity) {
        String sql = "INSERT INTO todo2(todo,user_id,password,created_at,updated_at) values(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getTodo());
            ps.setObject(2, entity.getUser().getId());
            ps.setObject(3, entity.getPassword());
            ps.setObject(4, entity.getCreatedAt());
            ps.setObject(5, entity.getUpdatedAt());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.changeId(id);

        return entity;
    }

    public Optional<Todo> findById(Long id) {
        String sql =
                """
                    SELECT 
                        t.id, 
                        t.todo, 
                        t.password, 
                        t.created_at, 
                        t.updated_at, 
                        m.id as manager_id, 
                        m.name as manager_name, 
                        m.email as manager_email,
                        m.created_at as manager_created_at,
                        m.updated_at as manager_updated_at
                    FROM todo2 t JOIN manager m ON t.manager_id = m.id 
                    WHERE t.id = ?
                """;

        List<Todo> result = jdbcTemplate.query(sql, rowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Todo> search(String date, String userName, int page, int size) {
        StringBuilder sql = new StringBuilder(
                """
                            SELECT 
                                t.id, 
                                t.todo, 
                                t.password, 
                                t.created_at, 
                                t.updated_at, 
                                m.id as user_id, 
                                m.username as user_name, 
                                m.password as user_password,
                                m.created_at as user_created_at,
                                m.updated_at as user_updated_at
                            FROM todo2 t 
                            JOIN user u ON t.user_id = u.id 
                            WHERE 1=1
                        """
        );

        List<Object> params = new ArrayList<>();

        if (date != null) {
            LocalDate searchDate = LocalDate.parse(date);
            LocalDateTime startOfDay = searchDate.atTime(LocalTime.MIN);
            LocalDateTime endOfDay = searchDate.atTime(LocalTime.MAX);

            sql.append(" AND t.updated_at BETWEEN ? AND ?");
            params.add(startOfDay);
            params.add(endOfDay);
        }

        if (userName != null) {
            sql.append(" AND u.username = ?");
            params.add(userName);
        }

        sql.append(" ORDER BY t.updated_at DESC");

        int offset = page * size;
        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), rowMapper(), params.toArray());
    }

    public void update(Todo entity) {
        String sql = "UPDATE todo2 SET todo = ?, user_id = ? WHERE id = ?";

        jdbcTemplate.update(sql, entity.getTodo(), entity.getUser().getId(), entity.getId());
    }

    public void delete(long todoId) {
        String sql = "DELETE FROM todo2 WHERE id = ?";
        jdbcTemplate.update(sql, todoId);
    }

    private RowMapper<Todo> rowMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getLong("user_id"),
                    rs.getString("user_name"),
                    rs.getString("user_password"),
                    rs.getTimestamp("user_created_at").toLocalDateTime(),
                    rs.getTimestamp("user_updated_at").toLocalDateTime()
            );

            return new Todo(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    user,
                    rs.getString("password"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }
}

