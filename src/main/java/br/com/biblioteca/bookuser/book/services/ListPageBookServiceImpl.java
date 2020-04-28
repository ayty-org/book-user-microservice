package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListPageBookServiceImpl implements ListPageBookService {
    private final BookRepository bookRepository;

    @Override
    public Page<Book> findPage(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return bookRepository.findAll(pageRequest);
    }
}
