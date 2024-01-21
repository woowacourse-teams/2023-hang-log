<p align="center">
  <a href="https://www.npmjs.com/package/hang-log-design-system">
    <img width="200" src="https://github.com/hang-log-design-system/design-system/assets/51967731/f0dcb43d-2f7b-4d29-b314-69f24b15184f">
  </a>
</p>

<h1 align="center">Hang Log Design System</h1>

A design system library for 행록(Hang Log), a place-based travel record service.

## Installation

```sh
$ npm install hang-log-design-system
# or
$ yarn add hang-log-design-system
```

## Getting started

To start using the components, first wrap your application in a provider provided by **hang-log-design-system**

```jsx
import { HangLogProvider } from 'hang-log-design-system';

const App = ({ children }) => {
  return <HangLogProvider>{children}</HangLogProvider>;
};
```

<br>

After adding the provider, now you can start using components like this.

```jsx
import { Button } from 'hang-log-design-system';

function App() {
  return (
    <Button variant="primary" size="large">
      Hello World
    </Button>
  );
}
```

## Links

- [Storybook](https://64ae1170f3ddc89ef85a4950-jugaezrbhx.chromatic.com/)
- [Figma](https://www.figma.com/file/rJUqeL7LUnJjCPQNmQ3BZc/design-system?type=design&node-id=1%3A2854&mode=design&t=nVD5D8xFhO9Dkg6g-1)

## Contributors

| <img src="https://avatars.githubusercontent.com/u/45068522?v=4" width="120" height="120"> | <img src ="https://avatars.githubusercontent.com/u/51967731?v=4" width="120" height="120"> | <img src ="https://avatars.githubusercontent.com/u/102305630?v=4" width="120" height="120"> |
| :---------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
|                         [슬링키](https://github.com/dladncks1217)                         |                          [애슐리](https://github.com/ashleysyheo)                          |                             [헤다](https://github.com/Dahyeeee)                             |
