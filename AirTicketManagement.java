import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;

abstract class Person {
    private String name;
    private int age;
    private String email;

    public Person(String name, int age, String email) {
        setName(name);
        setAge(age);
        setEmail(email);
    }

    public String getName() { return name; }

    public void setName(String name) {
        for (int i = 0; i < name.length(); i++)
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ')
                throw new IllegalArgumentException("Invalid input! Name cannot contain numbers or symbols.");
        this.name = name;
    }

    public int getAge() { return age; }

    public void setAge(int age) {
        if (age <= 0) throw new IllegalArgumentException(" Age must be positive!");
        this.age = age;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (!email.contains("@")) throw new IllegalArgumentException(" Invalid email format!");
        this.email = email;
    }

    public abstract String getDetails();
}


class Passenger extends Person {
    private String destination, gender, cls, passportNID, flightNumber, flightDateTime, seatNumber;
    private LocalDate date;
    private double price;

    public Passenger(String name, int age, String email, String destination, String gender,
                     LocalDate date, String cls, String passportNID, String flightNumber, String flightDateTime, String seatNumber,double price) {
        super(name, age, email);
        this.destination = destination;
        this.gender = gender;
        this.date = date;
        this.cls = cls;
        this.passportNID = passportNID;
        this.flightNumber = flightNumber;
        this.flightDateTime = flightDateTime;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getDestination() { return destination; }
    public String getGender() { return gender; }
    public LocalDate getDate() { return date; }
    public String getCls() { return cls; }
    public String getPassportNID() { return passportNID; }
    public String getFlightNumber() { return flightNumber; }
    public String getFlightDateTime() { return flightDateTime; }
    public String getSeatNumber() { return seatNumber; }
    public double getPrice(){ return price;}

    @Override
    public String getDetails() {
        return "\uD83E\uDDD1 Passenger: " + getName() +
                "\n\uD83C\uDF82 Age: " + getAge() +
                "\n\uD83D\uDCE7 Email: " + getEmail() +
                "\n\uD83D\uDEC2 Passport/NID: " + passportNID +
                "\n\uD83D\uDEEB Destination: " + destination +
                "\n⚧ Gender: " + gender +
                "\n\uD83D\uDCC5 Date: " + date +
                "\n\uD83D\uDCBA Class: " + cls +
                "\n\uD83D\uDCB0 price: " + price +
                "\n✈ Flight No: " + flightNumber +
                "\n\uD83E\uDE91 Seat: " + seatNumber +
                "\n\uD83D\uDD52 Time: " + flightDateTime;
    }
}

class TicketManagement {
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private final String FILE_NAME = "tickets.txt";

    public void insertPassenger(Passenger p) {
        passengers.add(p);
        saveToFile();
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Passenger p : passengers) writer.println(p.getDetails() + "\n");
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}


public class AirTicketManagement {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("✈ Airline Booking System ✈");
            frame.setSize(700, 650);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImageIcon bg = new ImageIcon("plane_bg.jpg");
            JLabel bgLabel = new JLabel(bg);
            frame.setContentPane(bgLabel);
            frame.setLayout(new GridLayout(14,2,5,5));

            TicketManagement tm = new TicketManagement();

            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField passportField = new JTextField();

            String[] locations = {"Dhaka", "Chittagong", "Sylhet", "Cox's Bazar", "Rajshahi"};
            JComboBox<String> fromBox = new JComboBox<>(locations);
            JComboBox<String> toBox = new JComboBox<>(locations);

            JRadioButton male = new JRadioButton("Male");
            JRadioButton female = new JRadioButton("Female");
            ButtonGroup genderGroup = new ButtonGroup();
            genderGroup.add(male); genderGroup.add(female);

            JTextField dateField = new JTextField();
            String[] classes = {"Economy Class", "Business Class"};
            JComboBox<String> classBox = new JComboBox<>(classes);

            JComboBox<String> flightNumberBox = new JComboBox<>();
            JTextField flightDateTimeField = new JTextField();
            JTextField seatNumberField = new JTextField();

            JLabel priceLabel = new JLabel("\uD83D\uDCB0 Ticket Price: Select route");
            JButton submitBtn = new JButton("\uD83D\uDED2 Book Ticket");


            frame.add(new JLabel("\uD83D\uDC64 Name:")); frame.add(nameField);
            frame.add(new JLabel("\uD83C\uDF82 Age:")); frame.add(ageField);
            frame.add(new JLabel("\uD83D\uDCE7 Email:")); frame.add(emailField);
            frame.add(new JLabel("\uD83D\uDEC2 Passport/NID:")); frame.add(passportField);
            frame.add(new JLabel("\uD83D\uDEEB From:")); frame.add(fromBox);
            frame.add(new JLabel("\uD83D\uDEEC To:")); frame.add(toBox);
            frame.add(new JLabel("⚧ Gender:"));
            JPanel genderPanel = new JPanel(); genderPanel.add(male); genderPanel.add(female); frame.add(genderPanel);
            frame.add(new JLabel("\uD83D\uDCC5 Date (DD-MM-YYYY):")); frame.add(dateField);
            frame.add(new JLabel("\uD83D\uDCBA Class:")); frame.add(classBox);
            frame.add(new JLabel("✈ Flight No:")); frame.add(flightNumberBox);
            frame.add(new JLabel("\uD83D\uDD52 Time:")); frame.add(flightDateTimeField);
            frame.add(new JLabel("\uD83E\uDE91 Seat No:")); frame.add(seatNumberField);
            frame.add(priceLabel); frame.add(submitBtn);

            frame.getContentPane().setBackground(new Color(220,240,255,200));
            priceLabel.setForeground(new Color(0,102,153));
            submitBtn.setBackground(new Color(0,153,204)); submitBtn.setForeground(Color.WHITE);


            final double[] priceHolder = new double[1];
            Runnable updateInfo = new Runnable() {
                @Override
                public void run() {
                    String from = (String) fromBox.getSelectedItem();
                    String to = (String) toBox.getSelectedItem();
                    String selectedClass = (String) classBox.getSelectedItem();

                    int basePrice = 0;
                    switch (from + "-" + to) {
                        case "Dhaka-Chittagong": basePrice = 4000; break;
                        case "Chittagong-Dhaka": basePrice = 4000; break;
                        case "Dhaka-Sylhet": basePrice = 3300; break;
                        case "Sylhet-Dhaka": basePrice = 3300; break;
                        case "Dhaka-Cox's Bazar": basePrice = 5500; break;
                        case "Cox's Bazar-Dhaka": basePrice = 5500; break;
                        case "Dhaka-Rajshahi": basePrice = 3000; break;
                        case "Rajshahi-Dhaka": basePrice = 3000; break;
                        case "Chittagong-Sylhet": basePrice = 3500; break;
                        case "Sylhet-Chittagong": basePrice = 3500; break;
                        case "Chittagong-Rajshahi": basePrice = 3400; break;
                        case "Rajshahi-Chittagong": basePrice = 3400; break;
                        case "Sylhet-Cox's Bazar": basePrice = 3800; break;
                        case "Cox's Bazar-Sylhet": basePrice = 3800; break;
                        case "Sylhet-Rajshahi": basePrice = 3200; break;
                        case "Rajshahi-Sylhet": basePrice = 3200; break;
                        case "Cox's Bazar-Rajshahi": basePrice = 3600; break;
                        case "Rajshahi-Cox's Bazar": basePrice = 3600; break;
                        default:
                            priceLabel.setText("💰 Ticket Price: Invalid Route!");
                            flightNumberBox.removeAllItems();
                            flightDateTimeField.setText("");
                            seatNumberField.setText("");
                            return;
                    }

                    double finalPrice = basePrice;
                    if ("Business Class".equals(selectedClass)) {
                        finalPrice = basePrice * 1.5;
                    }
                    priceHolder[0] = finalPrice;
                    priceLabel.setText("💰 Ticket Price: " + (int) finalPrice + " tk");

                    flightNumberBox.removeAllItems();
                    flightDateTimeField.setText("");
                    seatNumberField.setText("");

                    switch (from + "-" + to) {
                        case "Dhaka-Chittagong":
                            flightNumberBox.addItem("DC101");
                            flightDateTimeField.setText("10:00 AM");
                            break;
                        case "Chittagong-Dhaka":
                            flightNumberBox.addItem("CD102");
                            flightDateTimeField.setText("02:00 PM");
                            break;
                        case "Dhaka-Sylhet":
                            flightNumberBox.addItem("DS103");
                            flightDateTimeField.setText("09:00 AM");
                            break;
                        case "Sylhet-Dhaka":
                            flightNumberBox.addItem("SD104");
                            flightDateTimeField.setText("01:00 PM");
                            break;
                        case "Dhaka-Cox's Bazar":
                            flightNumberBox.addItem("DCB105");
                            flightDateTimeField.setText("08:00 AM");
                            break;
                        case "Cox's Bazar-Dhaka":
                            flightNumberBox.addItem("CBD106");
                            flightDateTimeField.setText("03:00 PM");
                            break;
                        case "Dhaka-Rajshahi":
                            flightNumberBox.addItem("DR107");
                            flightDateTimeField.setText("11:00 AM");
                            break;
                        case "Rajshahi-Dhaka":
                            flightNumberBox.addItem("RD108");
                            flightDateTimeField.setText("04:00 PM");
                            break;
                        case "Chittagong-Sylhet":
                            flightNumberBox.addItem("CS109");
                            flightDateTimeField.setText("12:00 PM");
                            break;
                        case "Sylhet-Chittagong":
                            flightNumberBox.addItem("SC110");
                            flightDateTimeField.setText("05:00 PM");
                            break;
                        case "Chittagong-Rajshahi":
                            flightNumberBox.addItem("CR111");
                            flightDateTimeField.setText("09:30 AM");
                            break;
                        case "Rajshahi-Chittagong":
                            flightNumberBox.addItem("RC112");
                            flightDateTimeField.setText("02:30 PM");
                            break;
                        case "Sylhet-Cox's Bazar":
                            flightNumberBox.addItem("SCB113");
                            flightDateTimeField.setText("07:00 AM");
                            break;
                        case "Cox's Bazar-Sylhet":
                            flightNumberBox.addItem("CBS114");
                            flightDateTimeField.setText("01:30 PM");
                            break;
                        case "Sylhet-Rajshahi":
                            flightNumberBox.addItem("SR115");
                            flightDateTimeField.setText("11:30 AM");
                            break;
                        case "Rajshahi-Sylhet":
                            flightNumberBox.addItem("RS116");
                            flightDateTimeField.setText("03:30 PM");
                            break;
                        case "Cox's Bazar-Rajshahi":
                            flightNumberBox.addItem("CBR117");
                            flightDateTimeField.setText("06:00 AM");
                            break;
                        case "Rajshahi-Cox's Bazar":
                            flightNumberBox.addItem("RCB118");
                            flightDateTimeField.setText("12:00 PM");
                            break;
                    }

                    Random rand = new Random();
                    seatNumberField.setText("Seat " + (1 + rand.nextInt(40)));
                }
            };

            fromBox.addActionListener(e -> updateInfo.run());
            toBox.addActionListener(e -> updateInfo.run());
            classBox.addActionListener(e -> updateInfo.run());
            updateInfo.run();

            submitBtn.addActionListener(e -> {
                try {
                    String name = nameField.getText();
                    if (name == null || name.isEmpty() ) {
                        JOptionPane.showMessageDialog(frame, "Name cannot be empty or numeric!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int age = Integer.parseInt(ageField.getText());
                    String email = emailField.getText();
                    String passport = passportField.getText();
                    String from = (String)fromBox.getSelectedItem();
                    String to = (String)toBox.getSelectedItem();
                    if (from.equals(to)) {
                        JOptionPane.showMessageDialog(frame, "From and To cannot be the same!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";
                    if (gender.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date;
                    try {
                        String dateInput = dateField.getText();
                        date = LocalDate.parse(dateInput, formatter);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(frame, " Invalid date! Please use DD-MM-YYYY format.","Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String cls = (String)classBox.getSelectedItem();
                    String flightNumber = (String)flightNumberBox.getSelectedItem();
                    String flightTime = flightDateTimeField.getText();
                    String seatNumber = seatNumberField.getText();
                    updateInfo.run();
                    double price = priceHolder[0];

                    Passenger p = new Passenger(name, age, email, from+" to "+to, gender, date, cls, passport, flightNumber, flightTime, seatNumber,price);
                    tm.insertPassenger(p);

                    JTextArea ticket = new JTextArea();
                    ticket.setEditable(false);
                    ticket.setFont(new Font("Monospaced", Font.BOLD, 14));
                    ticket.setBackground(new Color(255, 248, 220));
                    ticket.setForeground(Color.BLACK);
                    ticket.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                    ticket.setText(
                             "\uD83D\uDEEB✈✈ Airline Ticket ✈✈\uD83D\uDEEB\n\n" +
                                    "\uD83E\uDDD1 Name: " + p.getName() + "\n" +
                                    "\uD83C\uDF82 Age: " + p.getAge() + "\n" +
                                    "\uD83D\uDCE7 Email: " + p.getEmail() + "\n" +
                                    "\uD83D\uDEC2 Passport/NID: " + p.getPassportNID() + "\n\n" +
                                    "\uD83D\uDEEB Route: " + p.getDestination() + "\n" +
                                    "⚧ Gender: " + p.getGender() + "\n" +
                                    "\uD83D\uDCC5 Date: " + p.getDate() + "\n" +
                                    "\uD83D\uDCBA Class: " + p.getCls() + "\n" +
                                    "\uD83D\uDCB0 price :" + p.getPrice() + "\n"+
                                    "✈ Flight No: " + p.getFlightNumber() + "     \uD83E\uDE91Seat: " + p.getSeatNumber() + "\n" +
                                    "\uD83D\uDD52 Departure Time: " + p.getFlightDateTime() + "\n\n" +
                                    "──────────────────────────────\n" +
                                    "        Have a safe flight! ✈\n" +
                                    "──────────────────────────────"
                    );

                    JOptionPane.showMessageDialog(frame, new JScrollPane(ticket), "🛫 Ticket", JOptionPane.INFORMATION_MESSAGE);

                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame,"Age must be a number!","Error",JOptionPane.ERROR_MESSAGE);
                } catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

                }
            });

            frame.setVisible(true);
        });
    }
}
