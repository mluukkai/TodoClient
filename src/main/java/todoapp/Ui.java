package todoapp;

import java.awt.*;
import javax.swing.*;
import todoapp.model.TodoItem;

public class Ui implements Runnable {

    private JFrame frame;
    private JPanel loginPanel;
    private JPanel todoPanel;
    private JPanel panels;
    private JPanel listPanel;
    private JPanel addPanel;
    private JPanel signUpPanel;
    private JPanel wellcomePanel;
    private JPanel itemPanel;
    final static String LOGIN = "LOGIN";
    final static String TODO = "TODO";
    final static String ADD = "ADD";
    final static String SIGNUP = "SIGNUP";
    final static String WELLCOME = "WELLCOME";
     final static String ITEM = "ITEM";
    private TodoService todo;

    public void setTodo(TodoService todo) {
        this.todo = todo;
    }

    @Override
    public void run() {
        frame = new JFrame("Todoapp");
        frame.setPreferredSize(new Dimension(300, 400));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        createTodoPanel();
        createLoginPanel();
        createAddPanel();
        createSignUpPanel();
        creteWellcomePanel();
        creteItemPanel();

        panels = new JPanel(new CardLayout());
        panels.add(loginPanel, LOGIN);
        panels.add(todoPanel, TODO);
        panels.add(addPanel, ADD);
        panels.add(signUpPanel, SIGNUP);
        panels.add(wellcomePanel, WELLCOME);
        panels.add(itemPanel, ITEM);

        showPanel(LOGIN);

        frame.add(panels);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new SpringLayout());

        JLabel usernameLabel = new JLabel("username");
        usernameLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(usernameLabel);
        JTextField username = new JTextField();
        username.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(username);
        JLabel passwordLabel = new JLabel("password");
        passwordLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(passwordLabel);
        JTextField password = new JPasswordField();
        password.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(password);        
        
        SpringUtilities.makeCompactGrid(inputPanel,
                2, 2, 
                6, 6, 
                6, 6);     
        
        loginPanel.add(inputPanel,BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        JButton loginButton = new JButton("login");
        controlsPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String user = username.getText();
            String pw = password.getText();
            boolean ok = todo.login(user, pw);
            if (ok) {
                updateListPanel();
                showPanel(TODO);
            } else {
                JOptionPane.showMessageDialog(frame, "wrong password or username");
            }
        });        

        JButton signUpButton = new JButton("sign up");
        controlsPanel.add(signUpButton);
        
        signUpButton.addActionListener(e -> {
                showPanel(SIGNUP);
        });
        
        loginPanel.add(controlsPanel, BorderLayout.SOUTH);

    }

    private void createListPanel() {
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
    }

    private void createTodoPanel() {
        todoPanel = new JPanel(new BorderLayout());

        createListPanel();

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        todoPanel.add(scrollPane, BorderLayout.CENTER);

        JButton exitButton = new JButton("exit");
        todoPanel.add(exitButton, BorderLayout.SOUTH);
        exitButton.addActionListener(e -> {
            showPanel(LOGIN);
        });

        JButton addButton = new JButton("add");
        addButton.addActionListener(e -> {
            showPanel(ADD);
        });
        
        todoPanel.add(addButton, BorderLayout.NORTH);
    }

    private void createAddPanel() {
        addPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new SpringLayout());
       
        JLabel nameLabel = new JLabel("todo name");
        nameLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(nameLabel);

        JTextField name = new JTextField();
        name.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(name);
        
        JLabel descLabel = new JLabel("description");
        descLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(descLabel);

        JTextField desc = new JTextField();
        desc.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(desc);

        JLabel hoursLabel = new JLabel("hours");
        hoursLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(hoursLabel);

        JTextField hours = new JTextField();
        hours.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(hours);
        
        SpringUtilities.makeCompactGrid(inputPanel,
                3, 2, 
                6, 6, 
                6, 6);     
        
        addPanel.add(inputPanel,BorderLayout.CENTER);        
        
        JPanel controlsPanel = new JPanel();
        JButton addButton = new JButton("add");
        controlsPanel.add(addButton);
        JButton backButton = new JButton("back");
        controlsPanel.add(backButton);
        
        addPanel.add(controlsPanel, BorderLayout.SOUTH);
        
        addButton.addActionListener(e -> {
            String todoName = name.getText().replace("\\*s", "");
            String todoDesc = desc.getText().replace("\\*s", "");
            int todoHours = Integer.parseInt(hours.getText().replace("\\*s", ""));
            name.setText("");
            desc.setText("");
            hours.setText("");
            todo.addItem(new TodoItem(todoName, todoDesc, false, todoHours));
            updateListPanel();
            showPanel(TODO);
        });
        
        backButton.addActionListener(e -> {
            showPanel(TODO);
        });

    }
    
    private JComponent createItemPanel(TodoItem todoItem) {
        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        itemPanel.setLayout(new GridLayout(1, 2));
        JLabel label = new JLabel(todoItem.getName());
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));
        JButton doneButton = new JButton("done");
        JButton openButton = new JButton("open");
        controlPanel.add(doneButton);
        controlPanel.add(openButton);

        doneButton.addActionListener(p -> {
            todo.markDone(todoItem);
            updateListPanel();
        });

        openButton.addActionListener(p -> {
            updateItemPanel(todoItem);
            showPanel(ITEM);
        });    
        
        itemPanel.add(label);
        itemPanel.add(controlPanel);

        itemPanel.setPreferredSize(new Dimension(200, 100));
        itemPanel.setMinimumSize(new Dimension(200, 100));
        return itemPanel;
    }

    private void updateListPanel() {
        listPanel.removeAll();
        listPanel.revalidate();

        listPanel.repaint();
        for (TodoItem item : todo.undoneItems()) {
            listPanel.add(createItemPanel(item));
        }

        getFrame().repaint();
    }

    private void creteWellcomePanel() {
        wellcomePanel = new JPanel(new BorderLayout());
        wellcomePanel.add(new JLabel("welcome!"));
    }
    
    private void createSignUpPanel() {
        signUpPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new SpringLayout());
        
        JLabel usernameLabel = new JLabel("username");
        usernameLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(usernameLabel);
        JTextField username = new JTextField();
        username.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(username);
        
        JLabel nameLabel = new JLabel("name");
        nameLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(nameLabel);
        JTextField name = new JTextField();
        name.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(name);
        
        JLabel passwordLabel = new JLabel("password");
        passwordLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(passwordLabel);
        JTextField password = new JPasswordField();
        password.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(password);          
       
        SpringUtilities.makeCompactGrid(inputPanel,
                3, 2, 
                6, 6, 
                6, 6);        
        
        JPanel controlsPanel = new JPanel();
        JButton backButton = new JButton("back");
        JButton signUpButton = new JButton("sign up");
        controlsPanel.add(backButton);
        controlsPanel.add(signUpButton);
        
        signUpPanel.add(inputPanel, BorderLayout.CENTER);
        signUpPanel.add(controlsPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            showPanel(LOGIN);
        });     
        
        signUpButton.addActionListener(e -> {
            showPanel(WELLCOME);
        }); 
    }
    
    private void updateItemPanel(TodoItem item) {
        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());
        itemHours.setText(""+item.getEstimate());
    }
    
    private JTextField itemName;
    private JTextField itemDescription;
    private JTextField itemHours;
    
    private void creteItemPanel() {
        itemPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new SpringLayout());
       
        JLabel nameLabel = new JLabel("todo name");
        nameLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(nameLabel);

        itemName = new JTextField();
        itemName.setEnabled(false);
        itemName.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(itemName);
        
        JLabel descLabel = new JLabel("description");
        descLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(descLabel);

        itemDescription = new JTextField();
        itemDescription.setMaximumSize(new Dimension(100, 20));
        itemDescription.setEnabled(false);
        inputPanel.add(itemDescription);

        JLabel hoursLabel = new JLabel("hours");
        hoursLabel.setMaximumSize(new Dimension(100, 20));
        inputPanel.add(hoursLabel);

        itemHours = new JTextField();
        itemHours.setMaximumSize(new Dimension(100, 20));
        itemHours.setEnabled(false);
        inputPanel.add(itemHours);
        
        SpringUtilities.makeCompactGrid(inputPanel,
                3, 2, 
                6, 6, 
                6, 6);     
        
        itemPanel.add(inputPanel,BorderLayout.CENTER);        
        
        JPanel controlsPanel = new JPanel();
        JButton doneButton = new JButton("done");
        controlsPanel.add(doneButton);
        JButton backButton = new JButton("back");
        controlsPanel.add(backButton);
        
        itemPanel.add(controlsPanel, BorderLayout.SOUTH);
        
        doneButton.addActionListener(e -> {
            
            updateListPanel();
            showPanel(TODO);
        });
        
        backButton.addActionListener(e -> {
            showPanel(TODO);
        });   
    }
    
    private void showPanel(String card) {
        CardLayout cl = (CardLayout) (panels.getLayout());
        cl.show(panels, card);
    }

    public JFrame getFrame() {
        return frame;
    }    

}
