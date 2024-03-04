const adresses = () => {
  return [
    {
      id: '1',
      street: 'rua do java',
      number: 307,
      bairro: 'spring',
      cep: '18608-460',
    },
    {
      id: '2',
      street: 'rua do node',
      number: 307,
      bairro: 'graphql',
      cep: '18608-462',
    },
    {
      id: '3',
      street: 'rua kotlin',
      number: 307,
      bairro: 'spring',
      cep: '18608-461',
    },
  ];
};

const adress = () => {
  return {
    id: '1',
    street: 'rua do java',
    number: 307,
    bairro: 'spring',
    cep: '18608-460',
  };
};

export const adressResolvers = {
  Query: {
    adress,
    adresses,
  },
};
