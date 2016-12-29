/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.models;

import java.io.Serializable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

/**
 *
 * @author sergio
 */
public class DataTableProductInput extends DataTablesInput implements Serializable {

    private FilterProduct filterProduct = new FilterProduct();

    public FilterProduct getFilterProduct() {
        return filterProduct;
    }

    public void setFilterProduct(FilterProduct filterProduct) {
        this.filterProduct = filterProduct;
    }
}
