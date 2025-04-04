package org.example;
import org.example.Login.Login;
import org.example.Login.MenuPrincipal;

public class Principal_SGV {
    public static void main(String[] args) {

        Login login = new Login(); // llamamos al login

        // Esperamos hasta que el login sea exitosov
        while (!login.esAutenticado()) { //la variable en login debe   cambiar a true
            // Continuar mostrando la ventana de login hasta que el usuario se autentique
            try {
                Thread.sleep(500); // Esperamos un poco antes de volver a revisar el estado
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }

        // Si la autenticación es correcta, pasamos al menú
        System.out.println("Login exitoso. Iniciando el sistema...");
        MenuPrincipal menuPrincipal = new MenuPrincipal();


    }
}