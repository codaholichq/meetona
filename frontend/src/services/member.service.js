import { http } from "@/http";

const create = async (data) => {
  return await http.post("member", data).then(response => {
    return response.data;
  });
}

const getAll = async () => {
  return await http.get("member").then(response => {
    return response.data;
  });
}

const search = async (data) => {
  try {
    return await http.get(`member/search?email=${data}`).then(response => {
      return response.data;
    })
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export const memberService = {
  create,
  getAll,
  search
}
