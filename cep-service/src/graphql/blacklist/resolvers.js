const docs = () => {
  return [
    {
      id: '1',
      document: '33509878976',
    },
    {
      id: '2',
      document: '33560698766879',
    },
    {
      id: '3',
      document: '34567890546',
    },
    {
      id: '4',
      document: '33560698766839',
    },
  ];
};

const doc = () => {
  console.log('chamou aqui');
  return {
    id: '1',
    document: '33509878976',
  };
};

export const documentResolvers = {
  Query: {
    doc,
    docs,
  },
};
