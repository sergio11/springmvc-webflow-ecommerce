/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import persistence.models.Product;

/**
 *
 * @author sergio
 */
public interface ProductRepository extends DataTablesRepository<Product, Long> {}
