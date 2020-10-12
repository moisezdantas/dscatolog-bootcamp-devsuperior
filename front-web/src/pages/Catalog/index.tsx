import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ProductCard from "./components/ProductCard";
import './styles.scss';
import { makeRequest } from "../../core/utils/request";
import { ProductsResponse } from "../../core/types/Product";

const Catalog = () => {

  const [productsResponse, setProductsResponse] = useState<ProductsResponse>();


  useEffect(() => {
    const params = {
      page: 0,
      linesPerPage: 12,
    }

    makeRequest({url:'/products', params})
    .then( response => setProductsResponse(response.data));
  }, [])

  return (

    <div className="catalog-container">
      <div className="catalog-title">
          Catálogo de produtos
      </div>
      <div className="catalog-products">
        {productsResponse?.content.map(product => {
         return (
          <Link to={`/products/${product.id}`} key={product.id}>
            <ProductCard product={product}/>
        </Link>
         )
        })}
      </div>
    </div>
    
  );
};

export default Catalog;
