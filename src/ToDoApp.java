import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ToDoApp {

    private JFrame frame;
    private JTextField taskField;
    private JPanel taskPanel; // Панель для отображения задач
    private DBLogic db;

    public ToDoApp() {
        db = new DBLogic(); // Используем ваш существующий класс DBLogic
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("To-Do App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        taskField = new JTextField();
        frame.add(taskField, BorderLayout.NORTH);

        JButton addButton = new JButton("Add task");
        addButton.addActionListener(e -> addTask());
        frame.add(addButton, BorderLayout.SOUTH);

        // Панель для отображения задач
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS)); // Вертикальное расположение
        frame.add(new JScrollPane(taskPanel), BorderLayout.CENTER);

        loadTasks();
        frame.setVisible(true);
    }

    private void addTask() {
        String task = taskField.getText().trim();

        if (!task.isEmpty()) {
            db.addTask(task); // Используем метод addTask из DBLogic
            addTaskToPanel(task); // Добавляем задачу на панель
            taskField.setText("");
        }
    }

    private void addTaskToPanel(String task) {
        // Создаем панель для одной задачи
        JPanel singleTaskPanel = new JPanel(new BorderLayout());

        // Добавляем текст задачи
        JLabel taskLabel = new JLabel(task);
        singleTaskPanel.add(taskLabel, BorderLayout.CENTER);

        // Добавляем кнопку удаления
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.deleteTask(task); // Удаляем задачу из базы данных
                taskPanel.remove(singleTaskPanel); // Удаляем задачу с панели
                taskPanel.revalidate(); // Обновляем панель
                taskPanel.repaint(); // Перерисовываем панель
            }
        });
        singleTaskPanel.add(deleteButton, BorderLayout.EAST);

        // Добавляем задачу на основную панель
        taskPanel.add(singleTaskPanel);
        taskPanel.revalidate(); // Обновляем панель
        taskPanel.repaint(); // Перерисовываем панель
    }

    private void loadTasks() {
        List<String> tasks = db.getTasks(); // Используем метод getTasks из DBLogic
        for (String task : tasks) {
            addTaskToPanel(task); // Добавляем каждую задачу на панель
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoApp());
    }
}