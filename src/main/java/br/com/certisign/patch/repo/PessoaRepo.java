package br.com.certisign.patch.repo;

import br.com.certisign.patch.pojo.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepo extends PagingAndSortingRepository<Pessoa, Long> {

    /*
    @Query(value="SELECT DISTINCT p.nome FROM Pessoa p ORDER BY p.nome ASC")
    Iterable<Long> findDistinctNomeOrderByNomeAsc();

    Iterable<Pessoa> findByIdadeOrderByIdAsc(Long idade);
    */
}
