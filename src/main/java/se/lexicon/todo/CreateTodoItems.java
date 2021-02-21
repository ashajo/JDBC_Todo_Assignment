package se.lexicon.todo;

import se.lexicon.Db.MySQLConnection;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

public class CreateTodoItems implements ToDo{


    public CreateTodoItems() {
    }

    @Override
    public Todo create(Todo todo) {
        Todo createTodo = new Todo();
        String createQuery = "insert into todoit.todo_item(title,description,deadline,done,assignee_id) values(?,?,?,?,?);";
        try (
                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(createQuery,Statement.RETURN_GENERATED_KEYS);

        ){
            preparedStatement.setString(1,todo.getTitle());
            preparedStatement.setString(2,todo.getDescription());
            preparedStatement.setString(3,(todo.getDeadLine().toString()));
            preparedStatement.setBoolean(4,todo.isDone());
            preparedStatement.setInt(5,todo.getAssigneeId());

            int result = preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int idKey = 0;
            while (resultSet.next()) {
                idKey = resultSet.getInt(1);

            }
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public Collection<Todo> findAll() {
        Collection<Todo> todoCollection = new ArrayList<>();
        String findAllQuery = "select * from todoit.todo_item;";

        try (
                Statement statement = MySQLConnection.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(findAllQuery)) {
            while (resultSet.next()) {
                todoCollection.add(new Todo(resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDate.parse(resultSet.getDate("deadline").toString()),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoCollection;
    }

    @Override
    public Todo findById(int todo) {
        Todo toDo = new Todo();
        String queryFindById = "select * from todoit.todo_item where todo_id = ?;";

        try (
                PreparedStatement preparedStatement =MySQLConnection.getConnection().prepareStatement(queryFindById)) {
            preparedStatement.setInt(1,todo);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                toDo.setToDoId(resultSet.getInt("todo_id"));
                toDo.setTitle(resultSet.getString("title"));
                toDo.setDescription(resultSet.getString("description"));
                toDo.setDeadLine(LocalDate.parse(resultSet.getDate("deadline").toString()));
                toDo.setDone(resultSet.getBoolean("done"));
                toDo.setAssigneeId(resultSet.getInt("assignee_id"));
            }
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return toDo;
    }

    @Override
    public Collection<Todo> findByDoneStatus(boolean status) {
        Collection<Todo> todoCollection = new ArrayList<>();
        String queryFindByAssignee = "Select * from todoit.todo_item where done = ?;";

        try (

                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(queryFindByAssignee)) {
            preparedStatement.setBoolean(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                todoCollection.add(new Todo(resultSet.getString("todo_id"),
                        resultSet.getString("title"),
                        LocalDate.parse(resultSet.getDate("deadline").toString()),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")));
            }
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return todoCollection;


    }

    @Override
    public Collection<Todo> findByAssignee(int assignee) {
        Collection<Todo> todoCollection = new ArrayList<>();
        String queryFindByAssignee = "Select * from todoit.todo_item where assignee_id = ?;";

        try (

                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(queryFindByAssignee)) {
            preparedStatement.setInt(1,assignee);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                todoCollection.add(new Todo(resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDate.parse(resultSet.getDate("deadline").toString()),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")));

            }
        } catch (SQLException  e) {
            e.printStackTrace();
        }

        return todoCollection;
    }

    @Override
    public Collection<Todo> findByAssignee(Person person) {
        Collection<Todo> todoCollection = new ArrayList<>();
        String queryFindByAssignee = "Select * from todoit.todo_item where assignee_id = ?;";

        try (

                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(queryFindByAssignee)) {
            preparedStatement.setInt(1,person.getPersonId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                todoCollection.add(new Todo(resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDate.parse(resultSet.getDate("deadline").toString()),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")));
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return todoCollection;
    }

    @Override
    public Collection<Todo> findByUnassignedTodoItems() {
        Collection<Todo> todoCollection = new ArrayList<>();
        String queryFindByAssignee = "Select * from todoit.todo_item where assignee_id is null;";
        try (

                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(queryFindByAssignee);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                todoCollection.add(new Todo(resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDate.parse(resultSet.getDate("deadline").toString()),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoCollection;
    }

    @Override
    public Todo update(Todo todo) {
        if (todo.getToDoId() == 0) {
            throw new IllegalArgumentException("Exception you cannot UPDATE because it is not yet existed");

        }
        Todo updateTodo = new Todo();
        String updateQuery = "Update todoit.todo_item st title = ?, description = ?, deadline = ?, done = ?, assignee_id = ?;";

        try (

                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setString(1,todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setDate(3, Date.valueOf(LocalDate.parse(todo.getDeadLine().toString())));
            preparedStatement.setBoolean(4,todo.isDone());
            preparedStatement.setInt(5, todo.getAssigneeId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                updateTodo.setToDoId(resultSet.getInt("todo_id"));
                updateTodo.setTitle(resultSet.getString("title"));
                updateTodo.setDescription(resultSet.getString("description"));
                updateTodo.setDeadLine(LocalDate.parse(resultSet.getDate("deadline").toString()));
                updateTodo.setDone(resultSet.getBoolean("done"));
                updateTodo.setAssigneeId(resultSet.getInt("assignee_id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateTodo;
    }

    @Override
    public boolean deleteById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Exception you cannot UPDATE because it is not yet existed");

        }
        String deleteQuery = "Delete from todoit.todo_item where todo_id = ?;";
        try (
                PreparedStatement preparedStatement = MySQLConnection.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1,id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
