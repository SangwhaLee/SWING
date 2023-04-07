import styled from '@emotion/styled';

export const H1 = styled.h1`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: bold;
  font-size: 48px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const H2 = styled.h2`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: bold;
  font-size: 40px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const H3 = styled.h3`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: bold;
  font-size: 32px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const H4 = styled.h4`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: bold;
  font-size: 24px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const H5 = styled.h5`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: bold;
  font-size: 20px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const H6 = styled.h6`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: bold;
  font-size: 18px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const P1 = styled.p`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: regular;
  font-size: 18px;
  line-height: 1.6;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const P2 = styled.p`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: regular;
  font-size: 16px;
  line-height: 1.6;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;

export const SmText = styled.p`
  padding: ${(props) => (props.padding ? props.padding : 0)};
  margin: ${(props) => (props.margin ? props.margin : 0)};
  font-weight: regular;
  font-size: 12px;
  line-height: 1.4;
  color: ${(props) => props.color};
  text-shadow: ${(props) =>
    props.outline
      ? `-${props.outlineWeight}px 0 ${props.outline}, 0 ${props.outlineWeight}px ${props.outline},  ${props.outlineWeight}px 0 ${props.outline}, 0 -${props.outlineWeight}px ${props.outline}`
      : 'none'};
  text-align: ${(props) => (props.align ? props.align : 'left')};
`;
