FROM node:18-alpine
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install -g create-react-app

# Copy the main application
COPY . ./

# Arguments
ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL="http://127.0.0.1:8080/api"

# Build the application
RUN npm run build
CMD ["npm", "start"]