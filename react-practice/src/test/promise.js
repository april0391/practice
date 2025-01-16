// 1. Producer
const promise = new Promise((resolve, reject) => {
  console.log("dd");
  setTimeout(() => {
    // resolve("ellie");
    reject(new Error("error"));
  }, 1000);
});

// 2. Consumers: then, catch, finally
promise
  .then((value) => {
    console.log(value);
  })
  .catch((err) => {
    console.log(err);
  })
  .finally(() => {
    console.log("finally");
  });

// 3. Promise chaninig
const fetchNumber = new Promise((resolve, reject) => {
  setTimeout(() => {
    resolve(1);
  }, 1000);
});

fetchNumber
  .then((num) => num * 2)
  .then((num) => num * 3)
  .then((num) => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(num - 1);
      }, 1000);
    });
  })
  .then((num) => console.log(num));

const getHen = () => {
  new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve("닭");
    }, 1000);
  });
};
