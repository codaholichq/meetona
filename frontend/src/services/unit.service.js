import { http } from "@/http";

const create = async (data) => {
  return await http.post("unit", data).then(response => {
    return response.data;
  });
}

const getAll = async () => {
  return await http.get("unit").then(response => {
    return response.data;
  });
}

export const unitService = {
  create,
  getAll
}
