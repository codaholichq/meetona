import { http } from '@/http';

const create = async (data) => {
  return await http.post("user", data).then(response => {
    return response.data;
  });
}

const getAll = async () => {
  return await http.get("user").then(response => {
    return response.data;
  });
}

const remove = async (id) => {
  return await http.delete(`user/${id}`);
}

export const userService = {
  create,
  getAll,
  remove
}
