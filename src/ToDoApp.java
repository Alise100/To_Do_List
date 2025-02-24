import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ToDoApp {

    private JFrame frame;
    private JTextField taskField;
    private JPanel taskPanel;
    private DBLogic db;

    public ToDoApp() {
        db = new DBLogic();
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


        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        frame.add(new JScrollPane(taskPanel), BorderLayout.CENTER);

        loadTasks();
        frame.setVisible(true);
    }

    private void addTask() {
        String task = taskField.getText().trim();

        if (!task.isEmpty()) {
            db.addTask(task);
            addTaskToPanel(task);
            taskField.setText("");
        }
    }

    private void addTaskToPanel(String task) {

        JPanel singleTaskPanel = new JPanel(new BorderLayout());


        JLabel taskLabel = new JLabel(task);
        singleTaskPanel.add(taskLabel, BorderLayout.CENTER);


        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.deleteTask(task);
                taskPanel.remove(singleTaskPanel);
                taskPanel.revalidate();
                taskPanel.repaint();
            }
        });
        singleTaskPanel.add(deleteButton, BorderLayout.EAST);


        taskPanel.add(singleTaskPanel);
        taskPanel.revalidate();
        taskPanel.repaint();
    }

    private void loadTasks() {
        List<String> tasks = db.getTasks();
        for (String task : tasks) {
            addTaskToPanel(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoApp());
    }
}