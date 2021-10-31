public class AddressBook_withDaoDB {
    public static void main(String[] args) {
        Person person;
        PersonDao personDao = new PersonDaoImpl();

        System.out.println("--- inserting ...");
        person = new Person("woonsik", "anyang-si");
        personDao.insert(person);
        person = new Person("boonsik", "gangnam-gu");
        personDao.insert(person);

        System.out.println("--- finding all ...");
        for (Person p : personDao.findAll()) {
            System.out.println("reading... " + p);
        }

        System.out.println("--- updating ...");
        person = personDao.findAll().get(0);
        person.setName("handsome Guy");
        personDao.update(person.getId(), person);

        System.out.println("--- check updated ...");
        person = personDao.findById(1);
        if (person != null)
            System.out.println(person);

        System.out.println("--- deleting by id ...");
        personDao.deleteById(2);

        System.out.println("--- show all after delete ...");
        for (Person p : personDao.findAll()) {
            System.out.println("reading... " + p);
        }
    }
}
