package com.whitehacker.controllers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whitehacker.entities.User;
import com.whitehacker.entities.Book;
import com.whitehacker.security.CurrentUserFinder;
import com.whitehacker.services.BookService;
import com.whitehacker.services.UserService;
import com.whitehacker.utils.DateTracker;
import com.whitehacker.utils.FineCalculator;
import com.whitehacker.utils.ListInStringConverter;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    CurrentUserFinder currentUserFinder;

    @Autowired
    FineCalculator fineCalculator;

    @Autowired
    DateTracker dateTracker;

    @Autowired
    ListInStringConverter listConverter;

    private final int maximumWeeksToExtend = 3;

    @GetMapping
    public String userHome(Model model) {
        User currentUser = currentUserFinder.getCurrentUser();
        model.addAttribute("booksWithFines", fineCalculator.selectBooksWithFines(currentUser.getBooks()));
        model.addAttribute("currentUser", currentUser);
        return "user/user-home";
    }

    @GetMapping(value="/yourbooks")
    public String yourBooks(Model model) {
        User currentUser = currentUserFinder.getCurrentUser();
        List<Book> books = currentUser.getBooks();
        LinkedHashMap<Book, BigDecimal> booksWithFines = fineCalculator.getBooksWithFines(books);
        model.addAttribute("books", booksWithFines);
        return "user/user-your-books";
    }

    @PutMapping(value="/yourbooks/extend")
    public String extendRequest(@RequestParam BigDecimal fineAmount,
                                @RequestParam Long bookId,
                                @RequestParam int weeksToExtend,
                                Model model) {

        Book book = bookService.findById(bookId);
        int maximumWeeksToExtend = 3;
        int extensionsLeft = maximumWeeksToExtend - book.getTimesExtended();
        long daysTooLate = dateTracker.daysTooLate(book.getReturnDate());

        if (book.getTimesExtended() < maximumWeeksToExtend && fineAmount.compareTo(BigDecimal.valueOf(0)) == 0 && book.getReservedByUser() == null) {
            book.setReturnDate(book.getReturnDate().plusDays(7L * weeksToExtend));
            book.setTimesExtended(book.getTimesExtended() + weeksToExtend);
            bookService.save(book);
            return"redirect:/user/yourbooks/bookextended";

        } else if (fineAmount.compareTo(BigDecimal.valueOf(0)) > 0 && daysTooLate <= (extensionsLeft * 7L) && book.getReservedByUser() == null) {
            return "redirect:/user/yourbooks/payfine/" + bookId;

        } else {
            return "redirect:/user/yourbooks/bookcannotbeextended";

        }
    }

    @GetMapping(value="/yourbooks/payfine/{bookId}")
    public String payFine(Model model, @PathVariable (value="bookId") Long bookId) {

        Book book = bookService.findById(bookId);
        BigDecimal fine = fineCalculator.getFineOfBook(book);
        int weeksToExtend = dateTracker.getWeeksToExtendReturnDate(book);

        model.addAttribute("weeksToExtend", weeksToExtend);
        model.addAttribute("fine", fine);
        model.addAttribute("book", book);

        return "user/user-pay-fine";
    }

    @PostMapping(value="/yourbooks/dopayment")
    public String doPayment(@RequestParam int weeksToExtend,
                            @RequestParam BigDecimal fineAmount,
                            @RequestParam long bookId,
                            Model model) {
        Book currentBook = bookService.findById(bookId);
        model.addAttribute("fineAmount", fineAmount);
        model.addAttribute("weeksToExtend", weeksToExtend);
        model.addAttribute("book", currentBook);
        return "user/user-do-payment";
    }

    @GetMapping(value="/yourbooks/bookextended")
    public String bookExtended() {
        return "user/user-book-extended";
    }

    @GetMapping(value="/yourbooks/bookcannotbeextended")
    public String bookCanNotBeExtended() {
        return "user/user-book-can-not-be-extended";
    }

    @GetMapping(value="/browsebooks")
    public String browseBooks(@RequestParam (required=false) String title,
                              @RequestParam (required=false) String author,
                              @RequestParam (required=false) String showAllBooks,
                              @RequestParam (required=false) Long  reservedBookId,
                              @RequestParam (required=false) Long removeBookId,
                              @RequestParam (required=false) String reservedBookIdsInString,
                              Model model) {

        Set<Long> reservedBookIds = new LinkedHashSet<>();
        if (reservedBookIdsInString != null) reservedBookIds = listConverter.convertListInStringToSetInLong(reservedBookIdsInString);
        if (removeBookId != null) reservedBookIds.remove(removeBookId);
        if(reservedBookId != null) reservedBookIds.add(reservedBookId);

        Map<Book, String> listedBookReservations = dateTracker.listedBookReservations(reservedBookIds);

        List<Book> books;
        if (showAllBooks == null) books = bookService.searchBooks(title, author);
        else books = bookService.findAll();

        model.addAttribute("userHasFine", fineCalculator.hasFineOrNot(currentUserFinder.getCurrentUser()));
        model.addAttribute("listedBookReservations", listedBookReservations);
        model.addAttribute("reservedBookIds", reservedBookIds);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("showAllBooks", showAllBooks);
        model.addAttribute("books", books);
        return "user/user-browse-books";
    }

    @GetMapping(value="/FAQ")
    public String FAQ() {
        return "user/user-FAQ";
    }


    @PutMapping(value="/browsebooks/payreservation")
    public String payReservation(@RequestParam String reservedBookIdsInString,
                                 @RequestParam Double amountToPay,
                                 Model model) {

        model.addAttribute("amountToPay", amountToPay);
        model.addAttribute("reservedBookIdsInString", reservedBookIdsInString);
        return "user/user-pay-reservation";
    }

    @PutMapping(value="browsebooks/savereservation")
    public String saveBookReservations(@RequestParam String reservedBookIdsInString) {
        Set<Long> reservedBookIds = listConverter.convertListInStringToSetInLong(reservedBookIdsInString);
        dateTracker.setReserervationDatesAndReservedByCurrentUserForMultipleBooks(reservedBookIds);
        return "redirect:/user/yourreservations";
    }

    @GetMapping(value="/yourreservations")
    public String yourReservations(Model model) {
        User currentUser = currentUserFinder.getCurrentUser();
        model.addAttribute("reservedBooks", currentUser.getReservedBooks());
        return "user/user-your-reservations";
    }
}
