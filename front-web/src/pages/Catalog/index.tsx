import React from "react";
import ProductCard from "./components/ProductCard";
import './styles.scss';

const Catalog = () => {
  return (
    <div className="catalog-container">
      <div className="catalog-title">
          Catálogo de produtos
      </div>
      <div className="catalog-products">
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
        <ProductCard/>
      </div>
    </div>
  );
};

export default Catalog;
