package view;

//import interface_adapter.clear_users.ClearController;
import interface_adapter.clear_users.ClearController;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.switchtologin.SwitchToLoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(20);

    private final JTextField emailInputField = new JTextField(35);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(20);
    private final SignupController signupController;
    private final ClearController clearController;
    private final SwitchToLoginController switchToLoginController;

    private final JButton signUp;
    private final JButton cancel;
    private final JButton clear;
    private final JButton swithToLogin;

    public SignupView(SignupController controller,
                      SignupViewModel signupViewModel,
                      ClearController clearController,
                      SwitchToLoginController switchToLoginController) {
        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        this.clearController = clearController;
        this.switchToLoginController = switchToLoginController;
        signupViewModel.addPropertyChangeListener(this);
        SignupView self = this;

        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setFont(new Font(null, Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);
        LabelTextPanel emailInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.EMAIL_LABEL), emailInputField);


        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setFont(new Font(null, Font.BOLD, 15));
        buttons.add(signUp);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancel.setFont(new Font(null, Font.BOLD, 15));
        buttons.add(cancel);
        clear = new JButton(SignupViewModel.CLEAR_BUTTON_LABEL);
        clear.setFont(new Font(null, Font.BOLD, 15));
        buttons.add(clear);
        swithToLogin = new JButton(SignupViewModel.SWITCH_TO_LOGIN_BUTTON_LABEL);
        swithToLogin.setFont(new Font(null, Font.BOLD, 15));
        buttons.add(swithToLogin);

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword(),
                                    currentState.getEmail()
                            );

                            // clear the input fields
                            usernameInputField.setText("");
                            passwordInputField.setText("");
                            repeatPasswordInputField.setText("");
                            emailInputField.setText("");
                            currentState = signupViewModel.getState();
                            currentState.setUsername(usernameInputField.getText());
                            currentState.setPassword(passwordInputField.getText());
                            currentState.setRepeatPassword(repeatPasswordInputField.getText());
                            currentState.setEmail(emailInputField.getText());
                            signupViewModel.setState(currentState);
                        }
                    }
                }
        );

        clear.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(clear)) {
                            String usernames = clearController.getUsernames();
                            clearController.execute();
                            JOptionPane.showMessageDialog(self,
                                    "These users have been cleared:\n" + usernames);

                        }
                    }
                }
        );

        cancel.addActionListener(this);

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
        usernameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = usernameInputField.getText() + e.getKeyChar();
                        currentState.setUsername(text);
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        repeatPasswordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setRepeatPassword(repeatPasswordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState); // Hmm, is this necessary?
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        emailInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = emailInputField.getText() + e.getKeyChar();
                        currentState.setEmail(text);
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        swithToLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(swithToLogin)) {
                            switchToLoginController.execute();
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(emailInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }
}