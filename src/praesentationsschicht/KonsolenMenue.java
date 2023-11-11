package praesentationsschicht;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import datenhaltungsschicht.DBZugriff;
import logikschicht.Benutzer;
import logikschicht.Benutzerverwaltung;

public class KonsolenMenue {

    public static void startMenu() {
        try (Scanner scanner = new Scanner(System.in))
        {
            System.setProperty("file.encoding", "UTF-8");
            PrintStream out = new PrintStream(System.out, true, "UTF-8");

            while (true)
            {
                out.println("Benutzern-Datenbank-Operationen:");
                out.println("1. Neuen Benutzern hinzufügen");
                out.println("2. Benutzern aktualisieren");
                out.println("3. Benutzern löschen");
                out.println("4. Alle Benutzern auflisten");
                out.println("5. Benutzern anhand der benutzerId anzeigen");
                out.println("6. Beenden");

                out.print("Geben Sie Ihre Auswahl ein: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                String benutzerId;
                String anrede;
                String vorname;
                String nachname;
                String geburtsdatum;
                String email;
                String plz;
                String strasse;
                String refBenutzer;
                String ort;
                switch (choice)
                {
                    case 1:
                        out.print("Geben Sie die benutzerId ein: ");
                        benutzerId = scanner.nextLine();
                        out.print("Geben Sie die Anrede des Benutzern ein: ");
                        anrede = scanner.nextLine();
                        out.print("Geben Sie den Vornamen des Benutzern ein: ");
                        vorname = scanner.nextLine();
                        out.print("Geben Sie den Nachnamen des Benutzern ein: ");
                        nachname = scanner.nextLine();
                        out.print("Geben Sie das Geburtsdatum des Benutzern ein (TT.MM.JJJJ): ");
                        geburtsdatum = scanner.nextLine();
                        out.print("Geben Sie die E-Mail-Adresse des Benutzern ein: ");
                        email = scanner.nextLine();
                        out.print("Geben Sie die PLZ des Benutzern ein: ");
                        plz = scanner.nextLine();
                        out.print("Geben Sie den Ort des Benutzern ein: ");
                        ort = scanner.nextLine();
                        out.print("Geben Sie die Straße des Benutzern ein: ");
                        strasse = scanner.nextLine();
                        out.print("Geben Sie die refBenutzer des Benutzern ein: ");
                        refBenutzer = scanner.nextLine();
                        Benutzer newBenutzer = new Benutzer(benutzerId, anrede, vorname, nachname, geburtsdatum, email, plz, ort, strasse, refBenutzer);

                        if (Benutzerverwaltung.storeBenutzer(newBenutzer))
                        {
                            out.println("Der Benutzer wurde hinzugefügt.");
                        } else
                        {
                            out.println("Fehler beim Hinzufügen des Benutzern.");
                        }
                        break;
                    case 2:
                        out.print("Geben Sie die benutzerId des zu aktualisierenden Benutzern ein: ");
                        benutzerId = scanner.nextLine();
                        Benutzer benutzerToUpdate = Benutzerverwaltung.getBenutzerBybenutzerId(benutzerId);

                        if (benutzerToUpdate != null)
                        {
                            out.println("Benutzer gefunden. Aktuelle Daten:");
                            out.println(benutzerToUpdate.toString());

                            out.print("Geben Sie die neue Anrede ein (oder Enter, um unverändert zu lassen): ");
                            String newAnrede = scanner.nextLine();
                            if (!newAnrede.isEmpty())
                            {
                                benutzerToUpdate.setAnrede(newAnrede);
                            }

                            out.print("Geben Sie den neuen Vornamen ein (oder Enter, um unverändert zu lassen): ");
                            String newVorname = scanner.nextLine();
                            if (!newVorname.isEmpty())
                            {
                                benutzerToUpdate.setVorname(newVorname);
                            }

                            out.print("Geben Sie den neuen Nachnamen ein (oder Enter, um unverändert zu lassen): ");
                            String newNachname = scanner.nextLine();
                            if (!newNachname.isEmpty())
                            {
                                benutzerToUpdate.setNachname(newNachname);
                            }

                            out.print("Geben Sie das neue Geburtsdatum (TT.MM.JJJJ) ein (oder Enter, um unverändert zu lassen): ");
                            String newGeburtsdatum = scanner.nextLine();
                            if (!newGeburtsdatum.isEmpty())
                            {
                                benutzerToUpdate.setGeburtsdatum(newGeburtsdatum);
                            }

                            out.print("Geben Sie die neue E-Mail-Adresse ein (oder Enter, um unverändert zu lassen): ");
                            String newEmail = scanner.nextLine();
                            if (!newEmail.isEmpty())
                            {
                                benutzerToUpdate.setEmail(newEmail);
                            }

                            out.print("Geben Sie die neue PLZ ein (oder Enter, um unverändert zu lassen): ");
                            String newPLZ = scanner.nextLine();
                            if (!newPLZ.isEmpty())
                            {
                                benutzerToUpdate.setPlz(newPLZ);
                            }

                            out.print("Geben Sie den neuen Ort ein (oder Enter, um unverändert zu lassen): ");
                            String newOrt = scanner.nextLine();
                            if (!newOrt.isEmpty())
                            {
                                benutzerToUpdate.setOrt(newOrt);
                            }

                            out.print("Geben Sie die neue Straße ein (oder Enter, um unverändert zu lassen): ");
                            String newStrasse = scanner.nextLine();
                            if (!newStrasse.isEmpty())
                            {
                                benutzerToUpdate.setStrasse(newStrasse);
                            }

                            out.print("Geben Sie die neue refBenutzer ein (oder Enter, um unverändert zu lassen): ");
                            String newrefBenutzer = scanner.nextLine();
                            if (!newrefBenutzer.isEmpty())
                            {
                                benutzerToUpdate.setRefBenutzer(newrefBenutzer);
                            }

                            if (Benutzerverwaltung.updateBenutzer(benutzerToUpdate))
                            {
                                out.println("Benutzer erfolgreich aktualisiert. Aktualisierte Daten:");
                                out.println(benutzerToUpdate.toString());
                            } else
                            {
                                out.println("Fehler beim Aktualisieren des Benutzern.");
                            }
                        } else
                        {
                            out.println("Benutzer mit benutzerId " + benutzerId + " wurde nicht gefunden.");
                        }
                        break;

                    case 3:
                        out.print("Geben Sie die benutzerId des zu löschenden Benutzern ein: ");
                        benutzerId = scanner.nextLine();
                        Benutzer benutzerToDelete = Benutzerverwaltung.getBenutzerBybenutzerId(benutzerId);
                        if (benutzerToDelete != null)
                        {
                            if (Benutzerverwaltung.deleteBenutzer(benutzerToDelete))
                            {
                                out.println("Benutzer mit benutzerId " + benutzerId + " wurde erfolgreich gelöscht.");
                            } else
                            {
                                out.println("Fehler beim Löschen des Benutzern.");
                            }
                        } else
                        {
                            out.println("Benutzer mit benutzerId " + benutzerId + " wurde nicht gefunden.");
                        }
                        break;

                    case 4:
                        List<Benutzer> benutzern = Benutzerverwaltung.getAllBenutzern();
                        for (Benutzer benutzer : benutzern)
                        {
                            out.println(benutzer.toString());
                        }
                        break;

                    case 5:
                        out.print("Geben Sie die benutzerId des anzuzeigenden Benutzern ein: ");
                        benutzerId = scanner.nextLine();
                        Benutzer benutzerToDisplay = Benutzerverwaltung.getBenutzerBybenutzerId(benutzerId);
                        if (benutzerToDisplay != null)
                        {
                            out.println(benutzerToDisplay.toString());
                        } else
                        {
                            out.println("Benutzer mit benutzerId " + benutzerId + " wurde nicht gefunden.");
                        }
                        break;

                    case 6:
                        out.println("Anwendung wird beendet.");
                        DBZugriff.close();
                        System.exit(0);

                    default:
                        out.println("Ungültige Auswahl. Bitte versuchen Sie es erneut.");
                }
            }

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Das Programm wird leider beendet. ");
        }
    }
}
