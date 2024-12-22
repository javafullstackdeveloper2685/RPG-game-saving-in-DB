import EtityDTOs.Mage;
import EtityDTOs.Warrior;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("game_persistence_unit");
        EntityManager em = emf.createEntityManager();


        //здесь мы создаем игрока
        Mage mage = new Mage("Gandalf");
        Warrior warrior = new Warrior("Aragorn");

        // начало игры
        System.out.println("Das Spiel beginnt!\n");
        while (mage.getHealthPower() > 0 && warrior.getHealthPower() > 0) {
            // атака мага
            mage.attack(warrior);
            Thread.sleep(3000);
            if (warrior.getHealthPower() <= 0) {
                System.out.printf("%s is %s defeated!\n", mage.getName(), warrior.getName());
                mage.addExperience(10); // добавление опыта
                break;
            }

            // атака война
            warrior.attack(mage);
            Thread.sleep(3000);
            if (mage.getHealthPower() <= 0) {
                System.out.printf("%s is %s defeated!\n", warrior.getName(), mage.getName());
                warrior.addExperience(10); // добавление опыта
                break;
            }
        }
        // результаты игры
        persistPlayers(em, warrior, mage);

        em.close();
        emf.close();
    }

    public static void persistPlayers(EntityManager em, Warrior warrior, Mage mage) {

        em.getTransaction().begin();
        em.persist(warrior.createEntity(warrior));
        em.persist(mage.createEntity(mage));
        em.getTransaction().commit();
        System.out.println("Beide Spieler wurden erfolgreich gespeichert.");
    }



}

