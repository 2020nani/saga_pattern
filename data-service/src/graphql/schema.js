import { gql } from 'apollo-server-core';
import { documentResolvers } from './blacklist/resolvers';
import { documentTypeDefs } from './blacklist/typedefs';
import { adressResolvers } from './adress/resolvers';
import { adressTypeDefs } from './adress/typedefs';

const rootTypeDefs = gql`
  type Query {
    _empty: Boolean
  }
`;

const rootResolvers = {
  Query: {
    _empty: () => true,
  },
};

export const typeDefs = [rootTypeDefs, adressTypeDefs, documentTypeDefs];
export const resolvers = [rootResolvers, adressResolvers, documentResolvers];
