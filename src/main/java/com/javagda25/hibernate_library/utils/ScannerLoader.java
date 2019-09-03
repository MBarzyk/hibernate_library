package com.javagda25.hibernate_library.utils;

import com.javagda25.hibernate_library.model.Author;
import com.javagda25.hibernate_library.model.Book;
import com.javagda25.hibernate_library.model.BookLent;
import com.javagda25.hibernate_library.model.Client;
import com.javagda25.hibernate_library.dao.EntityDao;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class ScannerLoader {
    private final static Scanner scanner = new Scanner(System.in);

    public void initiate() {
        EntityDao entityDao = new EntityDao();
        String line;
        do {
            System.out.println("Give command: \n[addAuthor][addBook][addClient]\n[listAuthors]" +
                    "[listBooks][listClients]\n[deleteAuthor][deleteBook][deleteClient]\n" +
                    "[bindBookAuthor][lendBook]\n[exit]");
            line = scanner.nextLine();
            if (line.equalsIgnoreCase("addAuthor")) {
                addAuthor(entityDao);
            } else if (line.equalsIgnoreCase("addBook")) {
                addBook(entityDao);
            } else if (line.equalsIgnoreCase("addClient")) {
                addClient(entityDao);

            } else if (line.equalsIgnoreCase("listAuthors")) {
                entityDao.getall(Author.class).forEach(System.out::println);
            } else if (line.equalsIgnoreCase("listBooks")) {
                entityDao.getall(Book.class).forEach(System.out::println);
            } else if (line.equalsIgnoreCase("listClients")) {
                entityDao.getall(Client.class).forEach(System.out::println);
            } else if (line.equalsIgnoreCase("deleteAuthor")) {
                System.out.println("Delete which author? (give id)");
                Long id = Long.parseLong(scanner.nextLine());
                entityDao.delete(Author.class, id);
            } else if (line.equalsIgnoreCase("deleteBook")) {
                System.out.println("Delete which book? (give id)");
                Long id = Long.parseLong(scanner.nextLine());
                entityDao.delete(Book.class, id);
            } else if (line.equalsIgnoreCase("deleteClient")) {
                System.out.println("Delete which client? (give id)");
                Long id = Long.parseLong(scanner.nextLine());
                entityDao.delete(Client.class, id);
            } else if (line.equalsIgnoreCase("bindBookAuthor")) {
                bindAuthorWithBook(entityDao);
            } else if (line.equalsIgnoreCase("lendBook")) {
                lendBook(entityDao);
            }
//            } else if (line.equalsIgnoreCase("listInvoiceProducts")) {
//                System.out.println("List products of which invoice?: ");
//                Long invoiceId = Long.parseLong(scanner.nextLine());
//                invoiceDao.getProductsOfInvoice(invoiceId).forEach(System.out::println);
//            } else if (line.equalsIgnoreCase("getBill")) {
//                System.out.println("Get bill of which invoice?");
//                Long invoiceId = Long.parseLong(scanner.nextLine());
//                System.out.println("Money to pay: " + invoiceDao.getBillById(invoiceId));
//            } else if (line.equalsIgnoreCase("getUnpaid")) {
//                invoiceDao.getAllUnpaid().forEach(System.out::println);
//            } else if (line.equalsIgnoreCase("getLastWeek")) {
//                invoiceDao.getAllFromLastWeek().forEach(System.out::println);
//            } else if (line.equalsIgnoreCase("setAsPaid")) {
//                System.out.println("Give ID of invoice to set as paid: ");
//                Long id = Long.parseLong(scanner.nextLine());
//                invoiceDao.setInvoicePaid(id);
//            } else if (line.equalsIgnoreCase("handInvoice")) {
//                System.out.println("Give ID of invoice to hand out: ");
//                Long id = Long.parseLong(scanner.nextLine());
//                invoiceDao.handInvoice(id);
//            } else if (line.equalsIgnoreCase("getTodaySum")) {
//                System.out.println("Today's spendings reached: " + invoiceDao.getTotalPaymentsToday());

        } while (!line.equalsIgnoreCase("exit"));

    }


    private static void addAuthor(EntityDao dao) {
        Author author = new Author();
        System.out.println("Give author's name: ");
        author.setName(scanner.nextLine());
        System.out.println("Give author's surnname: ");
        author.setSurname(scanner.nextLine());
        do {
            System.out.println("Give author's date of birth: [yyyy-MM-dd]");
            author.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
        } while (author.getDateOfBirth() == null);


        dao.saveOrUpdate(author);
    }

    private static void addBook(EntityDao dao) {
        Book book = new Book();
        System.out.println("Give title ");
        book.setTitle(scanner.nextLine());
        System.out.println("Year of publish: ");
        book.setYearOfPublish(Integer.parseInt(scanner.nextLine()));
        System.out.println("How many pages does the book have?");
        book.setNumberOfPages(Integer.parseInt(scanner.nextLine()));

        dao.saveOrUpdate(book);
    }

    private void addClient(EntityDao dao) {
        Client client = new Client();
        System.out.println("Client's name: ");
        client.setName(scanner.nextLine());
        System.out.println("Client's surname: ");
        client.setSurname(scanner.nextLine());
        System.out.println("Client's PESEL: ");
        client.setPESEL(Long.parseLong(scanner.nextLine()));

        dao.saveOrUpdate(client);
    }

    private void addBookLend(EntityDao dao) {
        BookLent bookLent = new BookLent();
        System.out.println("Which book do you want to lend? (give book's ID)");
        Long bookId = Long.parseLong(scanner.nextLine());
        Optional<Book> optionalBook = dao.getById(Book.class, bookId);

        optionalBook.ifPresent(bookLent::setBook);

        System.out.println("To whom? (give Client's ID)");

        Long clientId = Long.parseLong(scanner.nextLine());
        Optional<Client> optionalClient = dao.getById(Client.class, clientId);

        optionalClient.ifPresent(bookLent::setClient);
        bookLent.setDateLent(LocalDate.now());

        dao.saveOrUpdate(bookLent);
    }

    private void bindAuthorWithBook(EntityDao dao) {

        System.out.println("Add which book?");
        Long bookId = Long.valueOf(scanner.nextLine());
        Optional<Book> bookOptional = dao.getById(Book.class, bookId);
        System.out.println("Add book to which author?");
        Long authorID = Long.valueOf(scanner.nextLine());
        Optional<Author> authorOptional = dao.getById(Author.class, authorID);
        if (authorOptional.isPresent() && bookOptional.isPresent()) {
            Author author = authorOptional.get();
            Book book = bookOptional.get();
            dao.saveOrUpdate(book);
            author.getBooks().add(book);
            dao.saveOrUpdate(author);
        }
    }

    private void lendBook(EntityDao dao) {
        BookLent bookLent = new BookLent();
        System.out.println("Lend which book?");
        Long bookId = Long.valueOf(scanner.nextLine());
        Optional<Book> bookOptional = dao.getById(Book.class, bookId);
        System.out.println("Lend book to which client?");
        Long clientId = Long.valueOf(scanner.nextLine());
        Optional<Client> clientOptional = dao.getById(Client.class, clientId);
        if (bookOptional.isPresent() && clientOptional.isPresent()) {
            Book book = bookOptional.get();
            Client client = clientOptional.get();
            bookLent.setDateLent(LocalDate.now());
            bookLent.setBook(book);
            bookLent.setClient(client);
            dao.saveOrUpdate(bookLent);
        }
    }
}
