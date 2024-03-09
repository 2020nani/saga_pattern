import { gql } from 'apollo-server-core';

export const adressTypeDefs = gql`
  extend type Query {
    adress(cep: ID!): Adress!
    adresses: [Adress!]!
  }

  type Adress {
    id: String!
    street: String!
    number: Int!
    bairro: String!
    cep: ID!
  }
`;
