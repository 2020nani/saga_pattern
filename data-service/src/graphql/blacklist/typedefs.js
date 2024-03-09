import { gql } from 'apollo-server-core';

export const documentTypeDefs = gql`
  extend type Query {
    doc(document: ID!): Document
    docs: [Document!]!
  }

  type Document {
    id: String!
    document: ID!
  }
`;
