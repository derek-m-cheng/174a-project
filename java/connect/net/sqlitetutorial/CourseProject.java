package net.sqlitetutorial;

import java.awt.*;  
import java.awt.event.*;
import javax.swing.*;  


    
public class CourseProject{  
    CardLayout card_frame;
    JFrame main_frame;
    JPanel card_panel;
    JLabel name_label;
    JLabel balance_label;

    CourseProject(){  
        card_frame = new CardLayout();
        main_frame = new JFrame();
        card_panel = new JPanel(card_frame);

        card_panel.add(setLoginFrame(), "login");
        card_panel.add(setUserLogin(), "user_login");
        card_panel.add(setManagerLogin(), "manager_login");
        card_panel.add(setUserReg(), "new_login");
        card_panel.add(setManagerInter(), "manager_inter");
        card_panel.add(setUserInter(), "user_inter");

        card_frame.show(card_panel, "login");
        main_frame.add(card_panel);
        main_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main_frame.setSize(1000,600);  
        main_frame.setVisible(true);
    }

    private JButton setButton(String text) {
        JButton b=new JButton(text);
        b.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        b.setBackground(Color.lightGray);
        b.setForeground(Color.black);
        return b;
    }

    // ------------------------------------------- General Login --------------------------------------------------
    private JPanel setLoginFrame(){
        JPanel panel = new JPanel();
            
        JButton b1 = setButton("Manager Login");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("manager login");
                card_frame.show(card_panel, "manager_login");
            }
        });
        JButton b2 = setButton("User Login");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("user login");
                card_frame.show(card_panel, "user_login");
            }
        });
        JButton b3 = setButton("New Account"); 
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("new user login");
                card_frame.show(card_panel, "new_login");
            }
        });
                
        panel.add(b1);panel.add(b2);panel.add(b3);
        panel.setVisible(true);
        panel.setLayout(new GridLayout(3,1));  
        return panel;
    }

    // ------------------------------------------- User Login --------------------------------------------------
    private JPanel setUserLogin() {
        JPanel panel=new JPanel();

        JPanel username_panel = new JPanel(new FlowLayout());
        JTextField username_field = new JTextField(20);
        JLabel username_label = new JLabel("Username");
        username_panel.add(username_label);
        username_panel.add(username_field);
        
        JPanel password_panel = new JPanel(new FlowLayout());
        JTextField password_field = new JTextField(20);
        JLabel password_label = new JLabel("Password");
        password_panel.add(password_label);
        password_panel.add(password_field);

        JButton b = setButton("User Login");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("user in");
                String c = Connect.userLogin(username_field.getText(), password_field.getText());
                if (c != null) {
                    name_label.setText(c);
                    int[] b = Connect.getBalance();
                    balance_label.setText(String.format("Market: %d$, Stock: %d$",b[0], b[1]));
                    card_frame.show(card_panel, "user_inter");
                } else {
                    System.out.println("login Failed");
                }
            }
        });

        panel.add(username_panel); panel.add(password_panel); panel.add(b);
        panel.setLayout(new GridLayout(3,1));  
        panel.setVisible(true);
        return panel;
    }

    // ------------------------------------------- User Registriation --------------------------------------------------
    private JPanel setUserReg() {
        JPanel panel=new JPanel();

        JPanel name_panel = new JPanel(new FlowLayout());
        JTextField name_field = new JTextField(20);
        JLabel name_label = new JLabel("Name");
        name_panel.add(name_label);
        name_panel.add(name_field);
        panel.add(name_panel);

        JPanel user_panel = new JPanel(new FlowLayout());
        JTextField user_field = new JTextField(20);
        JLabel user_label = new JLabel("Username");
        user_panel.add(user_label);
        user_panel.add(user_field);
        panel.add(user_panel);

        JPanel pass_panel = new JPanel(new FlowLayout());
        JTextField pass_field = new JTextField(20);
        JLabel pass_label = new JLabel("Password");
        pass_panel.add(pass_label);
        pass_panel.add(pass_field);
        panel.add(pass_panel);

        JPanel addr_panel = new JPanel(new FlowLayout());
        JTextField addr_field = new JTextField(20);
        JLabel addr_label = new JLabel("Address");
        addr_panel.add(addr_label);
        addr_panel.add(addr_field);
        panel.add(addr_panel);

        JPanel state_panel = new JPanel(new FlowLayout());
        JTextField state_field = new JTextField(20);
        JLabel state_label = new JLabel("State");
        state_panel.add(state_label);
        state_panel.add(state_field);
        panel.add(state_panel);

        JPanel phone_panel = new JPanel(new FlowLayout());
        JTextField phone_field = new JTextField(20);
        JLabel phone_label = new JLabel("Phone Number");
        phone_panel.add(phone_label);
        phone_panel.add(phone_field);
        panel.add(phone_panel);

        JPanel email_panel = new JPanel(new FlowLayout());
        JTextField email_field = new JTextField(20);
        JLabel email_label = new JLabel("Email");
        email_panel.add(email_label);
        email_panel.add(email_field);
        panel.add(email_panel);

        JPanel taxID_panel = new JPanel(new FlowLayout());
        JTextField taxID_field = new JTextField(20);
        JLabel taxID_label = new JLabel("Tax ID");
        taxID_panel.add(taxID_label);
        taxID_panel.add(taxID_field);
        panel.add(taxID_panel);

        JPanel SSN_panel = new JPanel(new FlowLayout());
        JTextField SSN_field = new JTextField(20);
        JLabel SSN_label = new JLabel("SSN");
        SSN_panel.add(SSN_label);
        SSN_panel.add(SSN_field);
        panel.add(SSN_panel);


        JPanel generate_field = new JPanel(new GridLayout(1,2));
        JButton user_button = setButton("new User");
        user_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("user in");
                Connect.registerUser(name_field.getText(), user_field.getText(),
                                    pass_field.getText(), addr_field.getText(),
                                    state_field.getText(), phone_field.getText(),
                                    email_field.getText(), taxID_field.getText(),
                                    SSN_field.getText(), false);
                name_label.setText(name_field.getText());
                card_frame.show(card_panel, "user_inter");
            }
        });
        JButton manager_button = setButton("new Manger");
        manager_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("manager in");
                Connect.registerUser(name_field.getText(), user_field.getText(),
                                    pass_field.getText(), addr_field.getText(),
                                    state_field.getText(), phone_field.getText(),
                                    email_field.getText(), taxID_field.getText(),
                                    SSN_field.getText(), false);
                card_frame.show(card_panel, "manager_inter");
            }
        });
        generate_field.add(user_button);
        generate_field.add(manager_button);
        panel.add(generate_field);


        panel.setLayout(new GridLayout(5,2));  
        return panel;
    }

    // ------------------------------------------- User Interface --------------------------------------------------
    private JPanel setUserInter() {
        JPanel panel=new JPanel();

        JPanel user_info = new JPanel(new GridLayout(2,1));
        name_label = new JLabel("temp name", SwingConstants.CENTER);
        balance_label = new JLabel("$ temp balance", SwingConstants.CENTER);
        user_info.add(name_label);
        
        user_info.add(balance_label);
        panel.add(user_info);

        JPanel despoit_panel = new JPanel(new FlowLayout());
        JTextField despoit_field = new JTextField(20);
        JLabel despoit_label = new JLabel("Deposit Amount");
        JButton despoit_button = setButton("Deposit");
        despoit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Deposit");
                Connect.updateMarket(Integer.parseInt(despoit_field.getText()));
                int[] b = Connect.getBalance();
                balance_label.setText(String.format("Market: %d$, Stock: %d$",b[0], b[1]));
            }
        });
        despoit_panel.add(despoit_label);
        despoit_panel.add(despoit_field);
        despoit_panel.add(despoit_button);
        panel.add(despoit_panel);

        JPanel withdraw_panel = new JPanel(new FlowLayout());
        JTextField withdraw_field = new JTextField(20);
        JLabel withdraw_label = new JLabel("Withdraw Amount");
        JButton withdraw_button = setButton("Withdraw");
        withdraw_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Deposit");
                Connect.updateMarket(-1*Integer.parseInt(withdraw_field.getText()));
                int[] b = Connect.getBalance();
                balance_label.setText(String.format("Market: %d$, Stock: %d$",b[0], b[1]));
            }
        });
        withdraw_panel.add(withdraw_label);
        withdraw_panel.add(withdraw_field);
        withdraw_panel.add(withdraw_button);
        panel.add(withdraw_panel);

        JPanel buy_panel = new JPanel(new FlowLayout());
        JTextField buy_field = new JTextField(20);
        JLabel buy_label = new JLabel("Stock Name");
        JTextField buy_stock_field = new JTextField(20);
        JLabel buy_stock_label = new JLabel("Number of Share");
        JButton buy_button = setButton("Buy");
        buy_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("buy stock");
                Connect.updateStock(Integer.parseInt(buy_stock_field.getText()), buy_field.getText());
                int[] b = Connect.getBalance();
                balance_label.setText(String.format("Market: %d$, Stock: %d$",b[0], b[1]));
            }
        });
        JPanel buy_stock_info = new JPanel(new GridLayout(2,1));
        JPanel buy_info = new JPanel();
        JPanel bstock_info = new JPanel();
        buy_info.add(buy_label);
        buy_info.add(buy_field);
        bstock_info.add(buy_stock_label);
        bstock_info.add(buy_stock_field);
        buy_stock_info.add(buy_info);
        buy_stock_info.add(bstock_info);
        buy_panel.add(buy_stock_info);
        buy_panel.add(buy_button);
        panel.add(buy_panel);


        JPanel sell_panel = new JPanel(new FlowLayout());
        JTextField sell_field = new JTextField(20);
        JLabel sell_label = new JLabel("Stock Name");
        JTextField sell_stock_field = new JTextField(20);
        JLabel sell_stock_label = new JLabel("Number of Share");
        JButton sell_button = setButton("Sell");
        sell_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("sell stock");
                Connect.updateStock(-1*Integer.parseInt(sell_stock_field.getText()), sell_field.getText());
                int[] b = Connect.getBalance();
                balance_label.setText(String.format("Market: %d$, Stock: %d$",b[0], b[1]));
            }
        });
        JPanel sell_stock_info = new JPanel(new GridLayout(2,1));
        JPanel sell_info = new JPanel();
        JPanel sstock_info = new JPanel();
        sell_info.add(sell_label);
        sell_info.add(sell_field);
        sstock_info.add(sell_stock_label);
        sstock_info.add(sell_stock_field);
        sell_stock_info.add(sell_info);
        sell_stock_info.add(sstock_info);
        sell_panel.add(sell_stock_info);
        sell_panel.add(sell_button);
        panel.add(sell_panel);

        JButton transaction_button = setButton("Transaction History");
        transaction_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("transaction");
                
            }
        });
        JPanel transaction_panel = new JPanel();
        transaction_panel.add(transaction_button);
        panel.add(transaction_panel);

        JPanel stock_info = new JPanel();
        // JTextField stocks_field = new JTextField();
        JButton stocks_button = setButton("Show My Stocks");
        stocks_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog f = new JDialog();
                String[] title = { "Actor ID", "Current Price", "Shares"};
                JTable tb = new JTable(Connect.getStocks(), title);
                f.setSize(1000,600); 
                f.add(new JScrollPane(tb));
                f.setVisible(true);
                System.out.println("show stocks");
            }
        });
        // stock_info.add(stocks_field);
        stock_info.add(stocks_button);
        panel.add(stock_info);

        JButton actors_button = setButton("Show Actors");
        actors_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog f = new JDialog();
                String[] title = { "Actor ID", "Current Price", "Name", "DOB", "Movie Title", "Role", "Year", "Contract"};
                JTable tb = new JTable(Connect.getMovies(), title);
                f.setSize(1000,600); 
                f.add(new JScrollPane(tb));
                f.setVisible(true);
                System.out.println("transaction");
            }
        });
        JPanel actor_panel = new JPanel();
        actor_panel.add(actors_button);
        panel.add(actor_panel);


        JButton movies_button = setButton("Show Movies");
        movies_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        JPanel movies_panel = new JPanel();
        movies_panel.add(movies_button);
        panel.add(movies_panel);

        panel.setLayout(new GridLayout(6,2));
        return panel;
    }


    // ------------------------------------------- Manager Login --------------------------------------------------
    private JPanel setManagerLogin() {
        JPanel panel=new JPanel();

        JPanel username_panel = new JPanel(new FlowLayout());
        JTextField username_field = new JTextField(20);
        JLabel username_label = new JLabel("Username");
        username_panel.add(username_label);
        username_panel.add(username_field);
        
        JPanel password_panel = new JPanel(new FlowLayout());
        JTextField password_field = new JTextField(20);
        JLabel password_label = new JLabel("Password");
        password_panel.add(password_label);
        password_panel.add(password_field);

        JButton b = setButton("Manager Login");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("manager in");
                String c = Connect.adminLogin(username_field.getText(), password_field.getText());
                if (c != null)
                    card_frame.show(card_panel, "manager_inter");
                else
                    System.out.println("login Failed");
            }
        });

        panel.add(username_panel); panel.add(password_panel); panel.add(b);
        panel.setLayout(new GridLayout(3,1));  
        panel.setVisible(true);
        return panel;
    }


    // ------------------------------------------- Manager Interface --------------------------------------------------
    private JPanel setManagerInter() {
        JPanel panel=new JPanel();

        JPanel interest_panel = new JPanel(new FlowLayout());
        JLabel interest_label = new JLabel("Interest percentage");
        JTextField interest_field = new JTextField(20);
        JButton b1 = setButton("Add Interest");
        panel.add(interest_panel);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add Interest");
                
            }
        });
        interest_panel.add(interest_label);
        interest_panel.add(interest_field);
        interest_panel.add(b1);

        JButton b2 = setButton("Generate Monthly Statement");
        panel.add(b2);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Monthly Statement");
            }
        });

        JButton b3 = setButton("List Active Customers");
        panel.add(b3);
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Active Customer");
            }
        });

        JButton b4 = setButton("Drug & Tax Evasion Report");
        panel.add(b4);
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("D&T report");
            }
        });

        JButton b5 = setButton("Customer Report");
        panel.add(b5);
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Customer Report");
            }
        });

        JButton b6 = setButton("Delete Transactions");
        panel.add(b6);
        b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete Transactions");
            }
        });

        JButton b7 = setButton("Open market");
        panel.add(b7);
        b7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open Market");
            }
        });

        JButton b8 = setButton("Close market");
        panel.add(b8);
        b8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Close Market");
            }
        });

        JPanel stock_panel = new JPanel(new FlowLayout());
        JLabel stock_label = new JLabel("stock name");
        JTextField stock_field = new JTextField(20);
        JLabel price_label = new JLabel("stock price");
        JTextField price_field = new JTextField(20);
        JButton b9 = setButton("Set Stock");
        panel.add(stock_panel);
        b9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Set Stock");
                Connect.setStock(Double.parseDouble(price_field.getText()), stock_field.getText());
            }
        });
        stock_panel.add(stock_label);
        stock_panel.add(stock_field);
        stock_panel.add(price_label);
        stock_panel.add(price_field);
        stock_panel.add(b9);

        JPanel date_panel = new JPanel(new FlowLayout());
        JLabel date_label = new JLabel("Date");
        JTextField date_field = new JTextField(20);
        JButton b10 = setButton("Set Date");
        panel.add(date_panel);
        b10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Set Date");
            }
        });
        date_panel.add(date_label);
        date_panel.add(date_field);
        date_panel.add(b10);

        
        panel.setLayout(new GridLayout(3,1));  
        panel.setVisible(true);
        return panel;
    }

    public static void main(String[] args) {  
        new CourseProject();
    }

}  
