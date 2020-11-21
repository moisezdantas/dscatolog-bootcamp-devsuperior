import Pagination from "core/components/Pagination";
import { ProductsResponse } from "core/types/Product";
import { makeRequest } from "core/utils/request";
import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import Card from "../Card";
import CardLoader from "../Loaders/ProductCardLoader";

const List = () => {
  const history = useHistory();
  const [productsResponse, setProductsResponse] = useState<ProductsResponse>();
  const [isLoading, setIsLoading] = useState(false);
  const [activePage, setActivePage] = useState(0);

  useEffect(() => {
    const params = {
      page: activePage,
      linesPerPage: 4,
      direction: "DESC",
      orderBy: "id",
    };

    setIsLoading(true);
    makeRequest({ url: "/products", params })
      .then((response) => setProductsResponse(response.data))
      .finally(() => {
        setIsLoading(false);
      });
  }, [activePage]);

  const handleCreate = () => {
    history.push("/admin/products/create");
  };

  return (
    <div className="admin-products-list">
      <button className="btn btn-primary btn-lg" onClick={handleCreate}>
        ADICIONAR
      </button>

      <div className="admin-list-container">
        {isLoading ? (
          <CardLoader />
        ) : (
          productsResponse?.content.map((product) => {
            return <Card product={product} key={product.id} />;
          })
        )}
      </div>
      {productsResponse && (
        <Pagination
          totalPages={productsResponse?.totalPages}
          activePage={activePage}
          onChange={(page) => setActivePage(page)}
        />
      )}
    </div>
  );
};
export default List;
