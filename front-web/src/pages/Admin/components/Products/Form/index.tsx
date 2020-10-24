import { makeRequest } from "core/utils/request";
import React, { useState } from "react";
import BaseForm from "../../BaseForm";
import "./styles.scss";

type FormState = {
  name: string;
  price: string;
  category: string;
  description: string;
};
type FormEvent = React.ChangeEvent<
  HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
>;

const Form = () => {
  const [formData, setFormData] = useState<FormState>({
    name: "Computador",
    price: "",
    category: "computador",
    description: "",
  });

  const handleOnChange = (event: FormEvent) => {
    const name = event.target.name;
    const value = event.target.value;

    setFormData((data) => ({ ...data, [name]: value }));
  };

  const handleSubmit = (event: React.ChangeEvent<HTMLFormElement>) => {
    event.preventDefault();

    const payload = {
      ...formData,
      imgUrl:
        "https://images7.kabum.com.br/produtos/fotos/115737/console-sony-playstation-5-midia-fisica_1598984720_gg.jpg",
      categories: [{ id: formData.category }],
    };

    makeRequest({ url: "/products", method: "POST", data: payload });
  };

  return (
    <form onSubmit={handleSubmit}>
      <BaseForm title="CADASTRAR UM PRODUTO">
        <div className="row">
          <div className="col-6">
            <input
              name="name"
              value={formData.name}
              type="text"
              className="form-control mb-5"
              onChange={handleOnChange}
              placeholder="Nome do producto"
            />
            <select
              name="category"
              value={formData.category}
              className="form-control mb-5"
              onChange={handleOnChange}
            >
              <option value="1">Livros</option>
              <option value="3">Computadores</option>
              <option value="2">Eletrônicos</option>
            </select>
            <input
              name="price"
              value={formData.price}
              type="text"
              className="form-control mb-5"
              onChange={handleOnChange}
              placeholder="Preço"
            />
          </div>
          <div className="col-6">
            <textarea
            className="form-control"
              name="description"
              value={formData.description}
              onChange={handleOnChange}
              cols={30}
              rows={10}
            />
          </div>
        </div>
      </BaseForm>
    </form>
  );
};
export default Form;
