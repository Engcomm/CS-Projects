#include "physicsEngine.h"

namespace gizmoball {

double TIME_SLICE = 0.05;

void PhysicsEngine::addObject(PhysicsObject physicsObject) {
	objects.push_back(physicsObject);
}

void PhysicsEngine::removeById(std::string id) {
	for (int i = 0; i < objects.size(); ++i) {
		if (objects[i].id == id) {
			objects.erase(objects.begin()+i);
			break;
		}
	}
}

void PhysicsEngine::setSpeed(std::string id, object_speed speed) {
	for (int i = 0; i < objects.size(); ++i) {
		if (objects[i].id == id) {
			objects[i].getSpeed()->copy(speed);
			break;
		}
	}
}

void PhysicsEngine::setBorder(std::string id, object_border border) {
	for (int i = 0; i < objects.size(); ++i) {
		if (objects[i].id == id) {
			objects[i].getBorder()->copy(border);
			break;
		}
	}
}

void PhysicsEngine::setFixed(std::string id, bool fixed) {
	for (int i = 0; i < objects.size(); ++i) {
		if (objects[i].id == id) {
			objects[i].setFixed(fixed);
			break;
		}
	}
}

std::vector<moveRecord> PhysicsEngine::getNext() {
	std::vector<PhysicsObject> checks;
	std::vector<moveRecord> results;
	for (int i = 0; i < objects.size(); i++) {
		objects[i].move();
		if (!objects[i].getFixed()) {
            results.push_back(moveRecord{objects[i].id, objects[i].getSpeed()->dx*0.2, objects[i].getSpeed()->dy*0.2});
			checks.push_back(objects[i]);
		}
	}

	for (auto o1 : checks) {
		for (auto o2 : objects) {
			if (o1.id != o2.id ) {
				bump(o1, o2, checkCollision(o1, o2));
			}
		}
	}

	return results;
}

object_border PhysicsEngine::checkCollision(PhysicsObject o1, PhysicsObject o2) {
	object_border result;
	for (int i = 0; i < o1.getBorder()->n ; i++) {
		for (int j = 0; j < o2.getBorder()->n; j++) {
			if (std::abs(o1.getBorder()->x[i]-o2.getBorder()->x[j]) < 0.5 && std::abs(o1.getBorder()->y[i]-o2.getBorder()->y[j])<0.5) {
				result.x[result.n] = o1.getBorder()->x[i];
				result.y[result.n] = o1.getBorder()->y[i];
				result.n++;
			}
		}
	}
	return result;
}


void PhysicsEngine::bump(PhysicsObject &o1, PhysicsObject &o2, object_border border) {
	if (border.n == 0) {
		return ;
	}

	if (border.n == 1) {
//		o1.getSpeed()->dx *= -1;
//		o1.getSpeed()->dy *= -1;
//		o2.getSpeed()->dx *= -1;
//		o2.getSpeed()->dy *= -1;
        return;
	} else {
		double x1 = border.x[0];
        double x2 = border.x[border.n-1];
        double y1 = border.y[0];
        double y2 = border.y[border.n-1];

        double nx;
        double ny;

        if (y2 == y1) {
            nx = 0;
            ny = 1;
        } else {
            nx = 1.0 / std::sqrt(1 + pow((x1 - x2) / (y2 - y1), 2));
            ny = ((x1 - x2) / (y2 - y1)) / std::sqrt(1 + pow((x1 - x2) / (y2 - y1), 2));
        }
        double sx = o1.getSpeed()->dx;
        double sy = o1.getSpeed()->dy;

        o1.getSpeed()->dx = sx - 2*(sx*nx + sy*ny)*nx;
        o1.getSpeed()->dy = sy - 2*(sx*nx + sy*ny)*ny;
	}

	if (!o2.getFixed()) {
		double x1 = (o1.getSpeed()->dx * (o1.getMass() - o2.getMass()) + 2*o2.getMass()*o2.getSpeed()->dx)
					/(o1.getMass() + o2.getMass());
		double y1 = (o1.getSpeed()->dy * (o1.getMass() - o2.getMass()) + 2*o2.getMass()*o2.getSpeed()->dy)
					/(o1.getMass() + o2.getMass());
		double x2 = (o2.getSpeed()->dx * (o2.getMass() - o1.getMass()) + 2*o1.getMass()*o1.getSpeed()->dx)
					/(o1.getMass() + o2.getMass());
		double y2 = (o2.getSpeed()->dy * (o2.getMass() - o1.getMass()) + 2*o1.getMass()*o1.getSpeed()->dy)
					/(o1.getMass() + o2.getMass());
		o1.getSpeed()->dx = x1;
		o1.getSpeed()->dy = y1;
		o2.getSpeed()->dx = x2;
		o2.getSpeed()->dy = y2;
	}

}

PhysicsObject* PhysicsEngine::getById(std::string id) {
    for (PhysicsObject& o : objects) {
        if (o.id == id) {
            return &o;
        }
    }
    return NULL;
}

void PhysicsEngine::removeAll() {
    for (int i = 0; i < objects.size(); ++i) {
        if (objects[i].id != "frame") {
            objects.erase(objects.begin() + i);
        }
    }
}
} // namespace gizmoball